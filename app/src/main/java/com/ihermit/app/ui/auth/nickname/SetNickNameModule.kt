package com.ihermit.app.ui.auth.nickname

import androidx.lifecycle.ViewModel
import com.ihermit.app.di.ViewModelBuilderModule
import com.ihermit.app.di.ViewModelClass
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SetNickNameModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilderModule::class
        ]
    )
    internal abstract fun setNickNameFragment(): SetNickNameFragment

    @Binds
    @IntoMap
    @ViewModelClass(SetNickNameViewModel::class)
    abstract fun bindViewModel(setNickNameViewModel: SetNickNameViewModel): ViewModel
}
