package com.ihermit.app.ui.auth.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ihermit.app.LoginNavDirections
import com.ihermit.app.R
import com.ihermit.app.databinding.LoginFragmentBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class LoginFragment : DaggerFragment(R.layout.login_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoginFragmentBinding.bind(view).setup()
    }

    private fun LoginFragmentBinding.setup() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect {
                when (it) {
                    LoginViewModel.Event.LoggedIn -> {
                        findNavController().navigate(LoginNavDirections.toMainActivity())
                        requireActivity().finish()
                    }
                    LoginViewModel.Event.Registered -> {
                        findNavController().navigate(LoginFragmentDirections.toLocationPermissionFragment())
                    }
                }
            }
        }

        fun auth() {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            viewModel.auth(email, password)
        }

        passwordField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                auth()
                true
            }
            false
        }
        authBtn.setOnClickListener {
            auth()
        }
    }
}
