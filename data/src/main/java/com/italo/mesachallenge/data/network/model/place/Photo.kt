package com.italo.mesachallenge.data.network.model.place


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "height")
    val height: Int = 0,
    @Json(name = "html_attributions")
    val htmlAttributions: List<Any> = listOf(),
    @Json(name = "photo_reference")
    val photoReference: String = "",
    @Json(name = "width")
    val width: Int = 0
)