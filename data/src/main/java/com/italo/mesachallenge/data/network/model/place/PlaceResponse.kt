package com.italo.mesachallenge.data.network.model.place


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceResponse(
    @Json(name = "html_attributions")
    val htmlAttributions: List<Any> = listOf(),
    @Json(name = "next_page_token")
    val nextPageToken: String = "",
    @Json(name = "results")
    val results: List<Result> = listOf(),
    @Json(name = "status")
    val status: String = ""
)