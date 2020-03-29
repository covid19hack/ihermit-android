package com.ihermit.app.data.network

import com.ihermit.app.data.entity.AuthResponse
import com.ihermit.app.data.entity.User
import com.ihermit.app.data.network.request.AuthRequest
import com.ihermit.app.data.network.request.CheckInRequest
import com.ihermit.app.data.network.request.UpdateUserRequestBody
import retrofit2.http.*

interface HermitService {
    @POST("users/authenticate")
    suspend fun auth(@Body authRequest: AuthRequest): AuthResponse

    @GET("users/profile")
    suspend fun getUser(): User

    @PATCH("users/updateNickName")
    suspend fun updateNickName(@Body request: UpdateUserRequestBody)

    @POST("users/checkIn")
    suspend fun checkIn(@Body request: CheckInRequest): User
}
