package com.ihermit.app.ui.auth

import com.ihermit.app.ui.auth.login.LoginModule
import com.ihermit.app.ui.auth.home.HomeSetupModule
import dagger.Module

@Module(
    includes = [
        LoginModule::class,
        HomeSetupModule::class
    ]
)
object AuthModule
