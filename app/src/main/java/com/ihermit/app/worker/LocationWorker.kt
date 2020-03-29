package com.ihermit.app.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.location.Location
import android.os.Build
import androidx.annotation.Keep
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.ihermit.app.R
import com.ihermit.app.data.preference.UserPreference
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val NOTIFICATION_ID = 5232

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
        setForeground(createForeground())
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
                delay(5000)
            } catch (e: Exception) {
                return Result.failure()
            }
        }
        return Result.success()
    }

    private fun createForeground(): ForegroundInfo {
        createChannelIfNecessary(applicationContext)
        val notification = NotificationCompat.Builder(applicationContext, "location-watcher")
            .setContentTitle("Home")
            .setTicker("Home")
            .setContentText("Looking at your house")
            .setSmallIcon(R.drawable.ic_my_location_black_24dp)
            .setOngoing(true)
//            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()

        return ForegroundInfo(NOTIFICATION_ID, notification)
    }

    private fun createChannelIfNecessary(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Location Watcher"
            val descriptionText = "Notifies user when they leave the home area."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationManager =
                context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(
                NotificationChannel("location-watcher", name, importance).apply {
                    description = descriptionText
                }
            )
        }
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
