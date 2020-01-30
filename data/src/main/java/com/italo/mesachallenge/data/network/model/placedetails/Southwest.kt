package com.italo.mesachallenge.data.network.model.placedetails


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Southwest(
    @Json(name = "lat")
    val lat: Double = 0.0,
    @Json(name = "lng")
    val lng: Double = 0.0
)