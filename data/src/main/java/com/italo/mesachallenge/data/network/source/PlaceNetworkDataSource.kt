package com.italo.mesachallenge.data.network.source

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.Place

interface PlaceNetworkDataSource {
    suspend fun getNearbyPlaces(
        lat: Double,
        lng: Double,
        radius: Int
    ): OneOf<Failure, List<Place>>
}