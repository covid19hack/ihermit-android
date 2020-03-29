package com.ihermit.app.ui.auth

import com.ihermit.app.ui.auth.home.HomeSetupModule
import com.ihermit.app.ui.auth.login.LoginModule
import com.ihermit.app.ui.auth.nickname.SetNickNameModule
import com.ihermit.app.ui.auth.permission.LocationPermissionModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AuthModule {

    @ContributesAndroidInjector(
        modules = [
            LoginModule::class,
            SetNickNameModule::class,
            HomeSetupModule::class,
            LocationPermissionModule::class
        ]
    )
    fun authActivity(): AuthActivity
}
