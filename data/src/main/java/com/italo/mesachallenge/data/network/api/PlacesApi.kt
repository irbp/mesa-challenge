package com.italo.mesachallenge.data.network.api

import com.italo.mesachallenge.data.network.model.place.PlaceResponse
import com.italo.mesachallenge.data.network.model.placedetails.PlaceDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PlacesApi {
    @GET("nearbysearch/json")
    suspend fun getNearbyPlacesAsync(@QueryMap options: Map<String, String>): Response<PlaceResponse>

    @GET("details/json")
    suspend fun getPlaceDetailsAsync(
        @Query("place_id") placeId: String,
        @Query("fields") fields: String,
        @Query("key") apiKey: String
    ): PlaceDetailsResponse
}