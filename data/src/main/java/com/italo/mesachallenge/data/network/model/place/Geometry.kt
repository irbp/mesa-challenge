package com.italo.mesachallenge.data.network.model.place


import com.italo.mesachallenge.data.network.model.placedetails.Viewport
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geometry(
    @Json(name = "location")
    val location: Location = Location(),
    @Json(name = "viewport")
    val viewport: Viewport = Viewport()
)