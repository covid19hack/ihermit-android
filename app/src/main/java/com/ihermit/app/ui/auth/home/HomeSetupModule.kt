package com.ihermit.app.ui.auth.home

import androidx.lifecycle.ViewModel
import com.ihermit.app.di.ViewModelBuilderModule
import com.ihermit.app.di.ViewModelClass
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeSetupModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilderModule::class
        ]
    )
    internal abstract fun homeSetupModule(): HomeSetupFragment

    @Binds
    @IntoMap
    @ViewModelClass(HomeSetupViewModel::class)
    abstract fun bindViewModel(homeSetupViewModel: HomeSetupViewModel): ViewModel

}
