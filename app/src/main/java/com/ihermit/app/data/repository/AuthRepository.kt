package com.ihermit.app.data.repository

import com.ihermit.app.data.network.HermitService
import com.ihermit.app.data.preference.UserPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val hermitService: HermitService,
    private val userPreference: UserPreference
) {
    suspend fun auth(email: String, password: String): Boolean {
        return hermitService.auth(email, password).also {
            userPreference.authToken = it.authToken
            userPreference.userId = it.userId
        }.newUser
    }
}
