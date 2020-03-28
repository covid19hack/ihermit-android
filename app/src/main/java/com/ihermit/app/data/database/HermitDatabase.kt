package com.ihermit.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ihermit.app.data.entity.Achievement
import com.ihermit.app.data.entity.User

@Database(
    entities = [
        Achievement::class,
        User::class
    ],
    version = 1
)
abstract class HermitDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
    abstract fun userDao(): UserDao
}
