package com.ihermit.app.ui.auth.permission

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ihermit.app.R
import com.ihermit.app.databinding.LocationPermissionFragmentBinding
import dagger.android.support.DaggerFragment
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.withPermissionsCheck


private val PERMISSIONS: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
} else {
    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
}

class LocationPermissionFragment : DaggerFragment(R.layout.location_permission_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LocationPermissionFragmentBinding.bind(view).setup()
    }

    private fun LocationPermissionFragmentBinding.setup() {
        grantPermission.setOnClickListener {
            withPermissionsCheck(
                *PERMISSIONS,
                onShowRationale = ::showLocationRationale,
                onPermissionDenied = ::retryLocationRequest,
                onNeverAskAgain = ::showNeedsLocation
            ) {
                findNavController().navigate(LocationPermissionFragmentDirections.toHomeSetupFragment())
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
