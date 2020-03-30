package com.ihermit.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.entity.Breach
import com.ihermit.app.data.entity.UserProfile

@Database(
    entities = [
        Achievement::class,
        UserProfile::class,
        Breach::class
    ],
    version = 1
)
@TypeConverters(
    value = [
        DateConverter::class
    ]
)
abstract class HermitDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
    abstract fun userDao(): UserDao
    abstract fun breachDao(): BreachDao
}
