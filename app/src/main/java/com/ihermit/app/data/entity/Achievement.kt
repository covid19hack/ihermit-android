package com.ihermit.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Achievement(
    @PrimaryKey val achievementId: Int,
    val achievementNumber: Int,
    val achievementName: String,
    val achievementDescription: String,
    val achievementCompleted: Boolean,
    val achievementProgress: Int,
    val achievementGoal: Int
)
