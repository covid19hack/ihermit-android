package com.ihermit.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Achievement(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val completed: Boolean,
    val progress: Int,
    val goal: Int,
    val points: Int,
    val imgUrl: String?,
    val userCanEdit: Boolean = false
)
