package com.ihermit.app.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
object NetworkModule {
    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()
    }

    @Provides
    @Singleton
    fun providesHermitService(moshi: Moshi, headerInterceptor: HeaderInterceptor): HermitService {
        val client = OkHttpClient.Builder()
            .apply { networkInterceptors().add(headerInterceptor) }
            .build()
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://example.com/api/v1/")
            .build()
            .create(HermitService::class.java)
    }
}
