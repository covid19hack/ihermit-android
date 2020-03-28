package com.ihermit.app.ui.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ihermit.app.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment(R.layout.login_fragment) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

}
