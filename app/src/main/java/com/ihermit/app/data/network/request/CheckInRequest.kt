package com.ihermit.app.data.network.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckInRequest(val atHome: Boolean)
