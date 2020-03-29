package com.ihermit.app.ui.main

import androidx.lifecycle.ViewModel
import com.ihermit.app.di.ViewModelBuilderModule
import com.ihermit.app.di.ViewModelClass
import com.ihermit.app.ui.main.achievement.AchievementModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilderModule::class,
            AchievementModule::class
        ]
    )
    internal abstract fun mainFragment(): MainFragment

    @Binds
    @IntoMap
    @ViewModelClass(MainViewModel::class)
    abstract fun bindViewModel(viewModel: MainViewModel): ViewModel
}
