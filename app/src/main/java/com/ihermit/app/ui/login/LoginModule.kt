package com.ihermit.app.ui.login

import androidx.lifecycle.ViewModel
import com.ihermit.app.di.ViewModelBuilderModule
import com.ihermit.app.di.ViewModelClass
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilderModule::class
        ]
    )
    internal abstract fun loginFragment(): LoginFragment

    @Binds
    @IntoMap
    @ViewModelClass(LoginViewModel::class)
    abstract fun bindViewModel(loginViewModel: LoginViewModel): ViewModel
}
