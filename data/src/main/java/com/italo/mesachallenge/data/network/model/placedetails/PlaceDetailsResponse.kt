package com.italo.mesachallenge.data.network.model.placedetails


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceDetailsResponse(
    @Json(name = "html_attributions")
    val htmlAttributions: List<Any> = listOf(),
    @Json(name = "result")
    val result: Result = Result(),
    @Json(name = "status")
    val status: String = ""
)