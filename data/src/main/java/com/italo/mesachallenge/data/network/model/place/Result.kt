package com.italo.mesachallenge.data.network.model.place


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "geometry")
    val geometry: Geometry = Geometry(),
    @Json(name = "icon")
    val icon: String = "",
    @Json(name = "id")
    val id: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "opening_hours")
    val openingHours: OpeningHours = OpeningHours(),
    @Json(name = "photos")
    val photos: List<Photo> = listOf(),
    @Json(name = "place_id")
    val placeId: String = "",
    @Json(name = "price_level")
    val priceLevel: Int = 0,
    @Json(name = "rating")
    val rating: Double = 0.0,
    @Json(name = "reference")
    val reference: String = "",
    @Json(name = "types")
    val types: List<String> = listOf(),
    @Json(name = "vicinity")
    val vicinity: String = "",
    @Json(name = "formatted_address")
    val formattedAddress: String = "",
    @Json(name = "permanently_closed")
    val permanentlyClosed: Boolean = false,
    @Json(name = "scope")
    val scope: String = "",
    @Json(name = "plus_code")
    val plusCode: PlusCode = PlusCode(),
    @Json(name = "user_ratings_total")
    val userRatingsTotal: Int = 0
)