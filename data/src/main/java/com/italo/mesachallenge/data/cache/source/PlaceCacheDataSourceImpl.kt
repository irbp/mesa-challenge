package com.italo.mesachallenge.data.cache.source

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.Place
import com.italo.mesachallenge.data.cache.database.LastPlaceDao
import com.italo.mesachallenge.data.cache.database.PlaceDetailsDao
import com.italo.mesachallenge.data.cache.mapper.PlaceMapper
import com.italo.mesachallenge.data.cache.model.LastPlaceCache
import java.io.IOException

class PlaceCacheDataSourceImpl(
    private val lastPlaceDao: LastPlaceDao,
    private val placeDetailsDao: PlaceDetailsDao
) : PlaceCacheDataSource {
    override suspend fun getNearbyPlaces(lat: Double, lng: Double): OneOf<Failure, List<Place>> {
        return try {
            val placesCache = placeDetailsDao.getNearbyPlaces(lat, lng)
            val places = placesCache.map { PlaceMapper.mapCacheToDomain(it) }
            OneOf.Success(places)
        } catch (e: IOException) {
            OneOf.Error(Failure.PlaceCacheFailure())
        }
    }

    override suspend fun getLastLocation(): OneOf<Failure, Pair<Double, Double>> {
        return try {
            val lastLocation = lastPlaceDao.getLastPlace()
            if (lastLocation == null) {
                OneOf.Success(Pair(0.0, 0.0))
            } else {
                OneOf.Success(Pair(lastLocation.lat, lastLocation.lng))
            }
        } catch (e: IOException) {
            OneOf.Error(Failure.PlaceCacheFailure())
        }
    }

    override suspend fun saveNearbyPlaces(lat: Double, lng: Double, places: List<Place>) {
        val placesDetailsCache = places.map { PlaceMapper.mapDomainToCache(lat, lng, it) }
        placeDetailsDao.insertPlaces(placesDetailsCache)
    }

    override suspend fun saveLastLocation(lat: Double, lng: Double) =
        lastPlaceDao.insertLastPlace(LastPlaceCache(lat, lng))

}