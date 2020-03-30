package com.ihermit.app.data.database

import android.content.Context
import androidx.room.Room
import com.ihermit.app.data.entity.Breach
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(applicationContext: Context): HermitDatabase {
        // TODO(malvinstn): Move to actual DB.
        return Room.databaseBuilder(
            applicationContext,
            HermitDatabase::class.java,
            "hermit_app"
        ).build()
    }

    @Provides
    fun providesUserDao(hermitDatabase: HermitDatabase): UserDao = hermitDatabase.userDao()

    @Provides
    fun providesAchievementDao(hermitDatabase: HermitDatabase): AchievementDao =
        hermitDatabase.achievementDao()

    @Provides
    fun providesBreachDao(hermitDatabase: HermitDatabase): BreachDao =
        hermitDatabase.breachDao()
}
