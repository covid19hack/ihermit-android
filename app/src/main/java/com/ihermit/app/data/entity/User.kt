package com.ihermit.app.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.*

@Entity
data class UserProfile(
    @PrimaryKey val _id: Long,
    val nickName: String,
    val levelNumber: Int,
    val levelName: String,
    val points: Int,
    val streakStartDate: Date,
    val streakLengthHours: Int,
    val numOfReasonableBreaches: Int
)

val UserProfile.streakDays
    get() = streakLengthHours / 24 + 1

@JsonClass(generateAdapter = true)
data class User(
    val _id: Long,
    val nickName: String,
    val levelNumber: Int,
    val levelName: String,
    val points: Int,
    val streakStartDate: Date,
    val streakLengthHours: Int,
    val numOfReasonableBreaches: Int,
    @Ignore
    val achievements: List<Achievement>
)
