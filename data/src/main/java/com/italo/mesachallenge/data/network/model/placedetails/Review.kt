package com.italo.mesachallenge.data.network.model.placedetails


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Review(
    @Json(name = "author_name")
    val authorName: String = "",
    @Json(name = "author_url")
    val authorUrl: String = "",
    @Json(name = "language")
    val language: String = "",
    @Json(name = "profile_photo_url")
    val profilePhotoUrl: String = "",
    @Json(name = "rating")
    val rating: Int = 0,
    @Json(name = "relative_time_description")
    val relativeTimeDescription: String = "",
    @Json(name = "text")
    val text: String = "",
    @Json(name = "time")
    val time: Int = 0
)