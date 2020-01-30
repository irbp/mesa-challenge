package com.italo.mesachallenge.data.cache.model

import androidx.room.Entity

@Entity(tableName = "last_place")
data class LastPlaceCache(
    val lat: Double,
    val lng: Double
)