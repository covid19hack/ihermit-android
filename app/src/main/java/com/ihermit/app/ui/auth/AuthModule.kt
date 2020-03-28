package com.ihermit.app.ui.auth

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ihermit.app.ui.auth.home.HomeSetupModule
import com.ihermit.app.ui.auth.login.LoginModule
import com.ihermit.app.ui.auth.permission.LocationPermissionModule
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        LoginModule::class,
        HomeSetupModule::class,
        LocationPermissionModule::class
    ]
)
object AuthModule {
    @Provides
    fun providesFusedLocationClient(applicationContext: Context): FusedLocationProviderClient {
        // TODO(malvinstn):  Move binding to activity.
        return LocationServices.getFusedLocationProviderClient(applicationContext)
    }
}
