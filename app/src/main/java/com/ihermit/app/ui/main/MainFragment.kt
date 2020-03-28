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


private val PERMISSIONS: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
} else {
    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
}

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
        location.setOnClickListener {
            withPermissionsCheck(
                *PERMISSIONS,
                onShowRationale = ::showLocationRationale,
                onPermissionDenied = ::retryLocationRequest,
                onNeverAskAgain = ::showNeedsLocation
            ) {
                LocationServices.getFusedLocationProviderClient(requireActivity())
                    .lastLocation
                    .addOnSuccessListener { location: Location? ->
                        Toast.makeText(requireActivity(), "Received location ", Toast.LENGTH_LONG)
                            .show()
                    }
            }
        }
    }

    private fun showNeedsLocation() {
        Toast.makeText(requireActivity(), "Needs location to work.", Toast.LENGTH_LONG).show()
    }

    private fun retryLocationRequest() {
        Toast.makeText(requireActivity(), "Please don't deny.", Toast.LENGTH_LONG).show()
    }

    private fun showLocationRationale(permissionRequest: PermissionRequest) {
        permissionRequest.proceed()
    }

}
