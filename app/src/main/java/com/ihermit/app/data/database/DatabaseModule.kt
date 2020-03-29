package com.ihermit.app.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(applicationContext: Context): HermitDatabase {
        // TODO(malvinstn): Move to actual DB.
        return Room.inMemoryDatabaseBuilder(
            applicationContext,
            HermitDatabase::class.java
        ).build()
    }

    @Provides
    fun providesUserDao(hermitDatabase: HermitDatabase): UserDao = hermitDatabase.userDao()

    @Provides
    fun providesAchievementDao(hermitDatabase: HermitDatabase): AchievementDao =
        hermitDatabase.achievementDao()
}
