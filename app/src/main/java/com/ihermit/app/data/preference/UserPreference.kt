package com.ihermit.app.data.preference

import android.content.SharedPreferences
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
}
