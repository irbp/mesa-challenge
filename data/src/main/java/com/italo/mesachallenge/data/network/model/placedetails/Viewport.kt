package com.italo.mesachallenge.data.network.model.placedetails


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Viewport(
    @Json(name = "northeast")
    val northeast: Northeast = Northeast(),
    @Json(name = "southwest")
    val southwest: Southwest = Southwest()
)