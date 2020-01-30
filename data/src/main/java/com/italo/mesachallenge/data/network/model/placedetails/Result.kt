package com.italo.mesachallenge.data.network.model.placedetails


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "address_components")
    val addressComponents: List<AddressComponent> = listOf(),
    @Json(name = "adr_address")
    val adrAddress: String = "",
    @Json(name = "formatted_address")
    val formattedAddress: String = "",
    @Json(name = "formatted_phone_number")
    val formattedPhoneNumber: String = "",
    @Json(name = "geometry")
    val geometry: Geometry = Geometry(),
    @Json(name = "icon")
    val icon: String = "",
    @Json(name = "id")
    val id: String = "",
    @Json(name = "international_phone_number")
    val internationalPhoneNumber: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "place_id")
    val placeId: String = "",
    @Json(name = "rating")
    val rating: Double = 0.0,
    @Json(name = "reference")
    val reference: String = "",
    @Json(name = "reviews")
    val reviews: List<Review> = listOf(),
    @Json(name = "types")
    val types: List<String> = listOf(),
    @Json(name = "url")
    val url: String = "",
    @Json(name = "utc_offset")
    val utcOffset: Int = 0,
    @Json(name = "vicinity")
    val vicinity: String = "",
    @Json(name = "website")
    val website: String = ""
)