package com.italo.domain.repository

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.Place

interface PlaceRepository {
    fun getNearbyPlaces(lat: Double, lng: Double, radius: Int): OneOf<Failure, List<Place>>
}