package com.italo.mesachallenge.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_details")
data class PlaceDetailsCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "place_id")
    val placeId: String,
    val name: String,
    @ColumnInfo(name = "formatted_address")
    val formattedAddress: String = "",
    @ColumnInfo(name = "formatted_number")
    val formattedPhone: String = "",
    val types: List<String>,
    val lat: Double,
    val lng: Double,
    @ColumnInfo(name = "nearby_lat")
    val nearbyLat: Double,
    @ColumnInfo(name = "nearby_lng")
    val nearbyLng: Double
)