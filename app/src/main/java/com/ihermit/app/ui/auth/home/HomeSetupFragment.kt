package com.ihermit.app.ui.auth.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.ihermit.app.R
import com.ihermit.app.databinding.HomeSetupFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

// TODO(malvinstn): Make this adjustable.
private const val HOME_RADIUS = 25.0

class HomeSetupFragment : DaggerFragment(R.layout.home_setup_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var fusedLocationServices: FusedLocationProviderClient

    private val viewModel by viewModels<HomeSetupViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HomeSetupFragmentBinding.bind(view).setup()
    }

    private fun HomeSetupFragmentBinding.setup() {
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync { gMap ->
            fusedLocationServices.lastLocation?.addOnSuccessListener {
                val latLng = LatLng(it.latitude, it.longitude)
                gMap.isMyLocationEnabled = true
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                val accuracy = it.accuracy
                // TODO(malvinstn): Zoom by accuracy
                gMap.moveCamera(CameraUpdateFactory.zoomTo(18.0F))
                val circle = gMap.addCircle(
                    CircleOptions()
                        .center(latLng)
                        .radius(HOME_RADIUS)
                        .strokeColor(Color.RED)
                )
                gMap.setOnCameraMoveListener {
                    circle.center = gMap.cameraPosition.target
                }
                continueBtn.setOnClickListener {
                    // Geofencing
                    Toast.makeText(requireActivity(), "Set: ${circle.center}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}
