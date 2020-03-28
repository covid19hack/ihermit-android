package com.ihermit.app.data.repository

import com.ihermit.app.data.network.HermitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val hermitService: HermitService
) {
}
