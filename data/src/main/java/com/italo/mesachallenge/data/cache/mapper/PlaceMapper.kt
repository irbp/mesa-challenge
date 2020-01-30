package com.italo.mesachallenge.data.cache.mapper

import com.italo.domain.model.Place
import com.italo.mesachallenge.data.cache.model.PlaceDetailsCache

object PlaceMapper {

    fun mapDomainToCache(
        nearbyLat: Double,
        nearbyLng: Double,
        placeDomain: Place
    ): PlaceDetailsCache {
        with(placeDomain) {
            return PlaceDetailsCache(
                placeId = placeId,
                name = name,
                formattedAddress = address,
                formattedPhone = phone,
                types = types,
                lat = lat,
                lng = lng,
                nearbyLat = nearbyLat,
                nearbyLng = nearbyLng
            )
        }
    }

    fun mapCacheToDomain(placeDetailsCache: PlaceDetailsCache): Place {
        with(placeDetailsCache) {
            return Place(
                placeId = placeId,
                name = name,
                address = formattedAddress,
                types = types,
                phone = formattedPhone,
                lat = lat,
                lng = lng
            )
        }
    }
}