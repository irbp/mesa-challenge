package com.italo.mesachallenge.data.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.italo.mesachallenge.data.cache.model.PlaceDetailsCache

@Dao
interface PlaceDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaces(places: List<PlaceDetailsCache>)

    @Query("SELECT * FROM place_details WHERE nearby_lat=:lat AND nearby_lng=:lng")
    fun getNearbyPlaces(lat: Double, lng: Double): List<PlaceDetailsCache>

    @Query("DELETE FROM place_details")
    fun removePlaces()
}