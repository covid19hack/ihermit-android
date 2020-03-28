package com.ihermit.app.ui.auth.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.ihermit.app.R
import com.ihermit.app.databinding.HomeSetupFragmentBinding
import com.ihermit.app.ui.main.MainActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

// TODO(malvinstn): Make this adjustable.
private const val HOME_RADIUS = 100.0

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
            var circle: Circle? = null
            fusedLocationServices.lastLocation?.addOnSuccessListener {
                // TODO(malvinstn): Use current locations and location update listener.
                val latLng = LatLng(it.latitude, it.longitude)
                gMap.isMyLocationEnabled = true
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                val accuracy = it.accuracy
                // TODO(malvinstn): Zoom by accuracy
                gMap.moveCamera(CameraUpdateFactory.zoomTo(15.0F))
                circle = gMap.addCircle(
                    CircleOptions()
                        .center(latLng)
                        .radius(HOME_RADIUS)
                        .strokeColor(Color.RED)
                )
                gMap.setOnCameraMoveListener {
                    viewModel.updateCenter(gMap.cameraPosition.target)
                }
                viewModel.updateLocation(it)
            }

            viewModel.center.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    circle?.center = it
                }
            })
            continueBtn.setOnClickListener {
                if (viewModel.saveHome()) {
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }
}
