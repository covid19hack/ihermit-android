package com.ihermit.app.data.preference

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserPreference @Inject constructor(
    @Named("user_preferences") private val sharedPreferences: SharedPreferences
) {
    val authToken: String? = sharedPreferences.getString("user_token", null)
}
