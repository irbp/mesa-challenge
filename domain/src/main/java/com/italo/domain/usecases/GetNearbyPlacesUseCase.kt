package com.italo.domain.usecases

import com.italo.domain.base.Failure
import com.italo.domain.base.LocationParams
import com.italo.domain.base.OneOf
import com.italo.domain.base.UseCase
import com.italo.domain.model.Place
import com.italo.domain.repository.PlaceRepository

class GetNearbyPlacesUseCase(private val placeRepository: PlaceRepository) :
    UseCase<List<Place>, LocationParams>() {

    override suspend fun run(params: LocationParams): OneOf<Failure, List<Place>> {
        return placeRepository.getNearbyPlaces(params.lat, params.lng, params.radius)
    }
}