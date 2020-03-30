package com.ihermit.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.*

@Entity
@JsonClass(generateAdapter = true)
data class Breach(
    @PrimaryKey val _id: String,
    val createdAt: Date
)
