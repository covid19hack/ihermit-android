package com.ihermit.app.ui.main

import android.Manifest
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.ihermit.app.R
import com.ihermit.app.databinding.MainFragmentBinding
import dagger.android.support.DaggerFragment
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.withPermissionsCheck
import javax.inject.Inject



class MainFragment : DaggerFragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainFragmentBinding.bind(view).setup()
    }

    private fun MainFragmentBinding.setup() {
    }
}
