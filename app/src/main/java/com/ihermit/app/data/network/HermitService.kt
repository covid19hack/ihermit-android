package com.ihermit.app.data.network

import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.entity.AuthResponse
import com.ihermit.app.data.entity.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HermitService {
    @POST("auth")
    suspend fun auth(email: String, password: String): AuthResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Long): User

    @GET("achievements")
    suspend fun getAchievements(): List<Achievement>
}