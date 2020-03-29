package com.ihermit.app.ui.auth.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.ihermit.app.LoginNavDirections
import com.ihermit.app.R
import com.ihermit.app.data.Constants
import com.ihermit.app.databinding.HomeSetupFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

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
        val radiusStroke = resources.getColor(R.color.homeRadiusStroke, root.context.theme)
        val radiusFill = resources.getColor(R.color.homeRadiusFill, root.context.theme)
        val radiusStrokeWidth = resources.getDimensionPixelSize(R.dimen.homeStrokeWidth).toFloat()
        val mapBottomPadding = resources.getDimensionPixelOffset(R.dimen.mapBottomPadding)
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync { gMap ->
            var circle: Circle? = null
            fusedLocationServices.lastLocation?.addOnSuccessListener {
                // TODO(malvinstn): Use current locations and location update listener.
                val latLng = LatLng(it.latitude, it.longitude)
                gMap.isMyLocationEnabled = true
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                // TODO(malvinstn): Zoom by accuracy
//              // TODO(malvinstn): Extract setup method
                val accuracy = it.accuracy
                gMap.moveCamera(CameraUpdateFactory.zoomTo(15.0F))
                gMap.uiSettings.isZoomGesturesEnabled = true
                gMap.uiSettings.isZoomControlsEnabled = true
                gMap.setPadding(0, 0, 0, mapBottomPadding)
                circle = gMap.addCircle(
                    CircleOptions()
                        .center(latLng)
                        .radius(Constants.HOME_RADIUS_IN_METER)
                        .strokeColor(radiusStroke)
                        .strokeWidth(radiusStrokeWidth)
                        .fillColor(radiusFill)
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
                    findNavController().navigate(LoginNavDirections.toMainActivity())
                    requireActivity().finish()
                }
            }
        }
    }
}
