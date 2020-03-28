package com.ihermit.app.data.preference

import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserPreference @Inject constructor(
    @Named("user_preferences") private val sharedPreferences: SharedPreferences
) {
    var authToken: String?
        get() = sharedPreferences.getString("authToken", null)
        set(value) = sharedPreferences.edit().putString("authToken", value!!).apply()

    var userId: Long?
        get() = sharedPreferences.getLong("userId", 0).takeIf { it != 0L }
        set(value) = sharedPreferences.edit().putLong("userId", value!!).apply()

    var home: LatLng?
        get() = sharedPreferences.getString("home", "")?.split(";")?.let {
            if (it.size == 2) {
                // TODO(malvinstn): Validate double.
                LatLng(it[0].toBigDecimal().toDouble(), it[1].toBigDecimal().toDouble())
            } else {
                null
            }
        }
        set(value) = if (value != null) {
            sharedPreferences.edit().putString(
                "home",
                "${value.latitude.toBigDecimal().toPlainString()};${value.longitude.toBigDecimal().toPlainString()}"
            ).apply()
        } else {
        }
}
