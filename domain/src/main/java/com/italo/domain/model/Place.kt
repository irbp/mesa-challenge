package com.italo.domain.model

data class Place(
    val placeId: String,
    val name: String,
    val address: String,
    val types: List<String>,
    val phone: String,
    val lat: Double,
    val lng: Double
)