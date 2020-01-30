package com.italo.mesachallenge.data.network.source

import android.content.Context
import android.util.Log
import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.Place
import com.italo.mesachallenge.data.R
import com.italo.mesachallenge.data.network.api.PlacesApi
import com.italo.mesachallenge.data.network.mapper.PlaceMapper

class PlaceNetworkDataSourceImpl(private val context: Context, private val placesApi: PlacesApi) :
    PlaceNetworkDataSource {

    private val TAG = PlaceNetworkDataSourceImpl::class.java.simpleName

    override suspend fun getNearbyPlaces(
        lat: Double,
        lng: Double,
        radius: Int
    ): OneOf<Failure, List<Place>> {
        val apiKey = context.getString(R.string.places_api_key)
        val fields = "place_id,name,formatted_address,geometry,type,formatted_phone_number"

        val queryParams = mapOf(
            Pair("location", "$lat,$lng"),
            Pair("radius", radius.toString()),
            Pair("key", apiKey)
        )
        Log.d(TAG, queryParams.toString())
        try {
            val nearbyPlaces = placesApi.getNearbyPlacesAsync(queryParams)
            if (!nearbyPlaces.isSuccessful) {
                return OneOf.Error(Failure.RetrieveNearbyFailure())
            }
            val placesId = nearbyPlaces.body()?.results?.map { it.placeId }
            val placesDetails = placesId?.map {
                placesApi.getPlaceDetailsAsync(placeId = it, fields = fields, apiKey = apiKey)
            }
            val places = placesDetails?.map { PlaceMapper.map(it) }

            return OneOf.Success(places!!)
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return OneOf.Error(Failure.ConnectionFailure())
        }
    }
}