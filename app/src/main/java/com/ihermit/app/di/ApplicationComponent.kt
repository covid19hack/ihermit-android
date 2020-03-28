package com.ihermit.app.di

import android.content.Context
import com.ihermit.app.HermitApplication
import com.ihermit.app.ui.main.MainModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelBuilderModule::class,
        MainModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<HermitApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
