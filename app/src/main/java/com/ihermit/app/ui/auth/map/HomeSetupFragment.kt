package com.ihermit.app.ui.auth.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ihermit.app.R
import com.ihermit.app.databinding.HomeSetupFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeSetupFragment : DaggerFragment(R.layout.home_setup_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeSetupViewModel> { viewModelFactory }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HomeSetupFragmentBinding.bind(view).setup()
    }

    private fun HomeSetupFragmentBinding.setup() {
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync {
            val sydney = LatLng(-34.0, 151.0)
            it.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            it.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }
}
