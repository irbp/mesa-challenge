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
import com.italo.domain.model.User
import com.italo.domain.usecases.GetCurrentLocationUseCase
import com.italo.domain.usecases.GetCurrentUserUseCase
import com.italo.domain.usecases.GetNearbyPlacesUseCase
import com.italo.mesachallenge.R
import com.italo.mesachallenge.data.gps.LocationProviderImpl

enum class PlacesTypes(val type: String, val icon: Int) {
    AIRPORT("airport", R.drawable.place_airport),
    BAR("bar", R.drawable.place_party),
    RESTAURANT("restaurant", R.drawable.place_restaurant),
    SHOPPING_MALL("shopping_mall", R.drawable.place_shopping),
    SUPERMARKET("supermarket", R.drawable.place_market),
}

class MainViewModel(
    private val useCase: GetNearbyPlacesUseCase,
    private val userUseCase: GetCurrentUserUseCase
) : BaseViewModel() {

    private val TAG = MainViewModel::class.java.simpleName

    private var getCurrentLocationUseCase: GetCurrentLocationUseCase? = null
    private val _currentLocation = MutableLiveData<CurrentLocation>()
    val currentLocation: LiveData<CurrentLocation> = _currentLocation
    private val _nearbyPlaces = MutableLiveData<List<Place>>()
    private val _currentNearbyPlaces = MutableLiveData<List<Place>>()
    val currentNearbyPlaces: LiveData<List<Place>> = _currentNearbyPlaces
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _pictureUrl = MutableLiveData<String>()
    val pictureUrl: LiveData<String> = _pictureUrl
    private val _liveLocation = MutableLiveData<Boolean>(true)
    val liveLocation: LiveData<Boolean> = _liveLocation

    private var currentCategoryIndex = 0

    fun getCurrentLocation(activity: Activity, onError: (Failure) -> Unit) {
        if (getCurrentLocationUseCase == null) {
            getCurrentLocationUseCase = GetCurrentLocationUseCase(LocationProviderImpl(activity))
        }
        getCurrentLocationUseCase?.execute(UseCase.NoParams()) {
            it.oneOf({ error -> onError(error) }, ::handleCurrentLocationChange)
        }
    }

    fun retrieveUserData(onError: (Failure) -> Unit) {
        userUseCase.execute(UseCase.NoParams()) {
            it.oneOf({ error -> onError(error) }, ::handleCurrentUser)
        }
    }

    private fun handleCurrentUser(user: User) {
        user.apply {
            _name.value = name
            _pictureUrl.value = pictureUrl
        }
    }

    fun changeCategory(categoryIndex: Int) {
        currentCategoryIndex = categoryIndex
        if (currentCategoryIndex != 0) {
            val category = PlacesTypes.values()[currentCategoryIndex - 1].type
            _currentNearbyPlaces.value = _nearbyPlaces.value!!.filter { place ->
                place.types.contains(category)
            }
        } else {
            _currentNearbyPlaces.value = _nearbyPlaces.value
        }
    }

    fun changeLiveLocation() {
        _liveLocation.value = !_liveLocation.value!!
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
        changeCategory(currentCategoryIndex)
    }

    override fun onCleared() {
        super.onCleared()
        getCurrentLocationUseCase?.cancel()
    }

    companion object {
        private const val RADIUS: Int = 50
    }
}