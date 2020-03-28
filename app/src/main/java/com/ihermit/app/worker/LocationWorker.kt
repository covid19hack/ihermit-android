package com.ihermit.app.worker

import android.content.Context
import android.location.Location
import androidx.annotation.Keep
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.ihermit.app.data.preference.UserPreference
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@ExperimentalCoroutinesApi
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
            val homeLocation = Location("").apply {
                latitude = home.latitude
                longitude = home.longitude
            }
            Timber.d("Has home! $home")
            try {
                val lastLocation = fusedLocationProviderClient.lastLocation()
                Timber.d("Last location is ${lastLocation.latitude}, ${lastLocation.longitude}")
                Timber.d("Distance: ${lastLocation.distanceTo(homeLocation)}")
            } catch (e: Exception) {
                return Result.failure()
            }
        }
        return Result.success()
    }

    private suspend fun FusedLocationProviderClient.lastLocation() =
        suspendCancellableCoroutine<Location> { cont ->
            lastLocation
                .addOnSuccessListener {
                    cont.resume(it)
                }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }
}
