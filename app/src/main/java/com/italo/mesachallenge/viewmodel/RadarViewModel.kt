package com.italo.mesachallenge.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.italo.domain.base.Failure
import com.italo.domain.base.LocationParams
import com.italo.domain.base.UseCase
import com.italo.domain.model.CurrentLocation
import com.italo.domain.model.Place
import com.italo.domain.usecases.GetCurrentLocationUseCase
import com.italo.domain.usecases.GetNearbyPlacesUseCase
import com.italo.mesachallenge.data.gps.LocationProviderImpl

class RadarViewModel(private val useCase: GetNearbyPlacesUseCase) : BaseViewModel() {

    private val TAG = RadarViewModel::class.java.simpleName

    private var getCurrentLocationUseCase: GetCurrentLocationUseCase? = null
    private val _currentLocation = MutableLiveData<CurrentLocation>()
    val currentLocation: LiveData<CurrentLocation> = _currentLocation
    private val _nearbyPlaces = MutableLiveData<List<Place>>()
    val nearbyPlaces: LiveData<List<Place>> = _nearbyPlaces

    fun getCurrentLocation(activity: Activity, onError: (Failure) -> Unit) {
        if (getCurrentLocationUseCase == null) {
            getCurrentLocationUseCase = GetCurrentLocationUseCase(LocationProviderImpl(activity))
        }
        getCurrentLocationUseCase?.execute(UseCase.NoParams()) {
            it.oneOf({ error -> onError(error) }, ::handleCurrentLocationChange)
        }
    }

    private fun handleCurrentLocationChange(location: CurrentLocation) {
        _currentLocation.value = location
        getNearbyPlaces(location.lat, location.lng)
    }

    private fun getNearbyPlaces(lat: Double, lng: Double) {
        useCase.execute(LocationParams(lat, lng, RADIUS)) {
            it.oneOf({ error -> { Log.e(TAG, error.toString()) } }, ::handleNearbyPlaces)
        }
    }

    private fun handleNearbyPlaces(nearbyPlaces: List<Place>) {
        _nearbyPlaces.value = nearbyPlaces
    }

    override fun onCleared() {
        super.onCleared()
        getCurrentLocationUseCase?.cancel()
    }

    companion object {
        private const val RADIUS: Int = 50
    }
}