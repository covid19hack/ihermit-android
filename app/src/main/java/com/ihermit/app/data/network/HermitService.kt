package com.ihermit.app.data.network

import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.entity.AuthResponse
import com.ihermit.app.data.entity.User
import com.ihermit.app.data.network.request.AuthRequest
import com.ihermit.app.data.network.request.UpdateUserRequestBody
import retrofit2.http.*

interface HermitService {
    @POST("authenticate")
    suspend fun auth(@Body authRequest: AuthRequest): AuthResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Long): User

    @PATCH("updateNickName/{id}")
    suspend fun updateNickName(@Path("id") id: Long, @Body request: UpdateUserRequestBody)
}
