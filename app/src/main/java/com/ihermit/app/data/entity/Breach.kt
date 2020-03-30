package com.ihermit.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Breach(
    @PrimaryKey val _id: String,
    val createdAt: Date
)
