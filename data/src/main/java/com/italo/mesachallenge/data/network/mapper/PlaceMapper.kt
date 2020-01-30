package com.italo.mesachallenge.data.network.mapper

import com.italo.domain.model.Place
import com.italo.mesachallenge.data.network.model.placedetails.PlaceDetailsResponse

object PlaceMapper {
    fun map(placeDetailsResponse: PlaceDetailsResponse): Place {
        with(placeDetailsResponse.result) {
            return Place(
                placeId = placeId,
                name = name,
                address = formattedAddress,
                types = types,
                phone = formattedPhoneNumber,
                lat = geometry.location.lat,
                lng = geometry.location.lng
            )
        }
    }
}