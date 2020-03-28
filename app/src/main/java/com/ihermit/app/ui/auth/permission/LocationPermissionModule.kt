package com.ihermit.app.ui.auth.permission

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationPermissionModule {

    @ContributesAndroidInjector
    internal abstract fun locationPermissionFragment(): LocationPermissionFragment

}
