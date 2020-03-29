package com.ihermit.app.data.network

import com.ihermit.app.data.preference.UserPreference
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val userPreference: UserPreference
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            userPreference.authToken?.let { authtoken ->
                chain.request().newBuilder()
                    .addHeader("X-AuthToken", authtoken)
                    .build()
            } ?: chain.request()
        )
    }
}
