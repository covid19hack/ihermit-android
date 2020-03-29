package com.ihermit.app.di

import android.content.Context
import com.ihermit.app.HermitApplication
import com.ihermit.app.data.DataModule
import com.ihermit.app.ui.auth.AuthModule
import com.ihermit.app.ui.main.MainModule
import com.ihermit.app.ui.main.achievement.dialog.AchievementDialogModule
import com.ihermit.app.worker.WorkManagerModule
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
        DataModule::class,
        MainModule::class,
        AchievementDialogModule::class,
        AuthModule::class,
        WorkManagerModule::class,
        LocationModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<HermitApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
