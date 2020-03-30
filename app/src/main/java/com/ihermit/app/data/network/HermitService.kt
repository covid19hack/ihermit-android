package com.ihermit.app.data.network

import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.entity.AuthResponse
import com.ihermit.app.data.entity.User
import com.ihermit.app.data.network.request.AuthRequest
import com.ihermit.app.data.network.request.CheckInRequest
import com.ihermit.app.data.network.request.UpdateUserRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface HermitService {
    @POST("users/authenticate")
    suspend fun auth(@Body authRequest: AuthRequest): AuthResponse

    @GET("users/profile")
    suspend fun getUser(): User

    @PATCH("users/updateNickName")
    suspend fun updateNickName(@Body request: UpdateUserRequestBody)

    @POST("users/checkIn")
    suspend fun checkIn(@Body request: CheckInRequest): User

    @PATCH("users/achievement")
    suspend fun updateAchievement(@Body achievement: Achievement): User

    @PATCH("checkIns/{breachId}/dismissBreach")
    suspend fun dismissBreach(): User
}
