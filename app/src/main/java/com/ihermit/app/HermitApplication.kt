package com.ihermit.app

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ihermit.app.di.DaggerApplicationComponent
import com.ihermit.app.worker.LocationWorker
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HermitApplication : DaggerApplication() {

    @Inject
    lateinit var workManager: WorkManager

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        super.onCreate()

        initPeriodicLocationWorker()
    }

    private fun initPeriodicLocationWorker() {
        workManager.enqueueUniquePeriodicWork(
            "locationWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<LocationWorker>(
                60, TimeUnit.MINUTES,
                15, TimeUnit.MINUTES
            ).build()
        )
    }
}
