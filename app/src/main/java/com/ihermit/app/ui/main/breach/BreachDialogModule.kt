package com.ihermit.app.ui.main.breach

import androidx.lifecycle.ViewModel
import com.ihermit.app.di.ViewModelBuilderModule
import com.ihermit.app.di.ViewModelClass
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface BreachDialogModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilderModule::class
        ]
    )
    fun breachDialogFragment(): BreachDialogFragment

    @Binds
    @IntoMap
    @ViewModelClass(BreachDialogViewModel::class)
    fun bindViewModel(breachDialogViewModel: BreachDialogViewModel): ViewModel
}
