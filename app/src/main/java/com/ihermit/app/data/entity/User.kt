package com.ihermit.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.*

@Entity
data class UserProfile(
    @PrimaryKey val _id: String,
    val nickName: String,
    val levelNumber: Int,
    val levelName: String,
    val points: Int,
    @ColumnInfo(defaultValue = "NULL")
    val streakStartDate: Date?,
    val streakLength: Int,
    val numOfReasonableBreaches: Int
)

val UserProfile.streakDays
    get() = streakLength / 24 + 1

@JsonClass(generateAdapter = true)
data class User(
    val _id: String,
    val nickName: String,
    val levelNumber: Int = 1,
    val levelName: String = "Newbie",
    val points: Int,
    val streakStartDate: Date?,
    val streakLength: Int,
    val numOfReasonableBreaches: Int = 0,
    @Ignore
    val achievements: List<Achievement>
)
