package com.ihermit.app.ui.auth.login

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ihermit.app.R
import com.ihermit.app.databinding.LoginFragmentBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : DaggerFragment(R.layout.login_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoginFragmentBinding.bind(view).setup()
    }

    private fun LoginFragmentBinding.setup() {
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer { isLoading ->
                email.isEnabled = !isLoading
                password.isEnabled = !isLoading
                authBtn.isEnabled = !isLoading
                if (isLoading) progress.show() else progress.hide()
            }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect {
                when (it) {
                    LoginViewModel.Event.LoggedIn -> {
                        findNavController().navigate(LoginFragmentDirections.toLocationPermissionFragment())
                    }
                    LoginViewModel.Event.Registered -> {
                        findNavController().navigate(LoginFragmentDirections.toSetNickNameFragment())
                    }
                }
            }
        }

        fun auth() {
            val email = emailField.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailField.error = getString(R.string.not_valid_email)
                return
            }
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
