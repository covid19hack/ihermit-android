package com.ihermit.app.ui.auth

import com.ihermit.app.ui.auth.home.HomeSetupModule
import com.ihermit.app.ui.auth.login.LoginModule
import com.ihermit.app.ui.auth.permission.LocationPermissionModule
import dagger.Module

@Module(
    includes = [
        LoginModule::class,
        HomeSetupModule::class,
        LocationPermissionModule::class
    ]
)
object AuthModule
