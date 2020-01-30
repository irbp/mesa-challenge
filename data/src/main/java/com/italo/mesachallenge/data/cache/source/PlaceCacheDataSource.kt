package com.italo.mesachallenge.data.cache.source

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.Place

interface PlaceCacheDataSource {
    suspend fun getNearbyPlaces(lat: Double, lng: Double): OneOf<Failure, List<Place>>

    suspend fun getLastLocation(): OneOf<Failure, Pair<Double, Double>>

    suspend fun saveNearbyPlaces(lat: Double, lng: Double, places: List<Place>)

    suspend fun saveLastLocation(lat: Double, lng: Double)
}