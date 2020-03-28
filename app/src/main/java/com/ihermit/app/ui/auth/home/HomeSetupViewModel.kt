package com.ihermit.app.ui.auth.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.ihermit.app.data.preference.UserPreference
import javax.inject.Inject

class HomeSetupViewModel @Inject constructor(
    private val userPreference: UserPreference
) : ViewModel() {
    private val _center: MutableLiveData<LatLng?> = MutableLiveData()
    val center: LiveData<LatLng?> = _center

    fun updateCenter(center: LatLng) {
        _center.value = center
    }

    fun updateLocation(location: Location) {
        _center.value = LatLng(location.latitude, location.longitude)
    }

    fun saveHome(): Boolean {
        val value = center.value
        return if (value != null) {
            userPreference.home = value
            true
        } else {
            false
        }
    }
}
