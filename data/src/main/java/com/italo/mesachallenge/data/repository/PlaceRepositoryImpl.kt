package com.italo.mesachallenge.data.repository

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.Place
import com.italo.domain.repository.PlaceRepository
import com.italo.mesachallenge.data.network.source.PlaceNetworkDataSource
import kotlinx.coroutines.runBlocking

class PlaceRepositoryImpl(private val networkSource: PlaceNetworkDataSource) : PlaceRepository {
    override fun getNearbyPlaces(
        lat: Double,
        lng: Double,
        radius: Int
    ): OneOf<Failure, List<Place>> = runBlocking {
        networkSource.getNearbyPlaces(lat, lng, radius)
    }
}