package com.ihermit.app.worker

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module(
    includes = [
        WorkManagerModule.Provider::class
    ]
)
interface WorkManagerModule {
    @ContributesAndroidInjector
    fun locationWorker(): LocationWorker

    @Module
    object Provider {
        @Provides
        @Singleton
        fun providesWorkManager(context: Context): WorkManager {
            return WorkManager.getInstance(context)
        }
    }
}
