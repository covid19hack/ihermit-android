package com.ihermit.app.data.network

import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.entity.AuthResponse
import com.ihermit.app.data.entity.User
import com.ihermit.app.data.network.request.UpdateUserRequestBody
import retrofit2.http.*

interface HermitService {
    @POST("auth")
    suspend fun auth(email: String, password: String): AuthResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Long): User

    @PATCH("users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body request: UpdateUserRequestBody)

    @GET("achievements")
    suspend fun getAchievements(): List<Achievement>
}
