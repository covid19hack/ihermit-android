package com.ihermit.app.ui.main.achievement

import androidx.lifecycle.ViewModel
import com.ihermit.app.di.ViewModelBuilderModule
import com.ihermit.app.di.ViewModelClass
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface AchievementModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilderModule::class
        ]
    )
    fun achievementFragment(): AchievementFragment

    @Binds
    @IntoMap
    @ViewModelClass(AchievementViewModel::class)
    fun bindViewModel(achievementViewModel: AchievementViewModel): ViewModel
}
