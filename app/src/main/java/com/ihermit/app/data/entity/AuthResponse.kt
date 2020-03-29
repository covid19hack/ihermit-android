package com.ihermit.app.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(val authToken: String, val userId: String, val newUser: Boolean)
