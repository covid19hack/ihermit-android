package com.ihermit.app.ui.main.achievement.dialog

import androidx.lifecycle.ViewModel
import com.ihermit.app.di.ViewModelBuilderModule
import com.ihermit.app.di.ViewModelClass
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface AchievementDialogModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilderModule::class
        ]
    )
    fun achievementDialogFragment(): AchievementDialogFragment

    @Binds
    @IntoMap
    @ViewModelClass(AchievementDialogViewModel::class)
    fun bindViewModel(achievementDialogViewModel: AchievementDialogViewModel): ViewModel
}
