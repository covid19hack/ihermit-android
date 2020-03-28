package com.ihermit.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Achievement(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String
)
