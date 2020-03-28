package com.ihermit.app.worker

import android.content.Context
import androidx.annotation.Keep
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.ihermit.app.data.preference.UserPreference
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

@Keep
class LocationWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    @Inject
    lateinit var userPreference: UserPreference

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    init {
        (context.applicationContext as HasAndroidInjector).androidInjector().inject(this)
    }

    override suspend fun doWork(): Result {
        Timber.d("doWork in LocationWorker")
        userPreference.home?.let { home ->
            Timber.d("Has home! $home")
            fusedLocationProviderClient.lastLocation?.addOnSuccessListener { location ->
                Timber.d("Last location is ${location.latitude}, ${location.longitude}")
            }
        }
        return Result.success()
    }
}
