package com.ihermit.app.ui.auth.nickname

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ihermit.app.R
import com.ihermit.app.databinding.SetNickNameFragmentBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class SetNickNameFragment : DaggerFragment(R.layout.set_nick_name_fragment) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SetNickNameViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SetNickNameFragmentBinding.bind(view).setup()
    }

    private fun SetNickNameFragmentBinding.setup() {
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer { isLoading ->
                continueBtn.isEnabled = !isLoading
                nickName.isEnabled = !isLoading
                if (isLoading) progress.show() else progress.hide()
            }
        )

        viewLifecycleOwner.lifecycleScope
            .launch {
                viewModel.events.collect { event ->
                    when (event) {
                        SetNickNameViewModel.Event.Updated -> {
                            findNavController().navigate(SetNickNameFragmentDirections.toLocationPermissionFragment())
                        }
                    }
                }
            }

        nickNameField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                updateNickName()
                true
            } else {
                false
            }
        }
        continueBtn.setOnClickListener {
            updateNickName()
        }
    }

    private fun SetNickNameFragmentBinding.updateNickName() {
        if (!nickNameField.text.isNullOrEmpty()) {
            viewModel.updateNickName(nickNameField.text.toString())
        }
    }
}
