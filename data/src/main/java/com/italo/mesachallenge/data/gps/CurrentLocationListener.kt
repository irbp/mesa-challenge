package com.italo.mesachallenge.data.gps

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.CurrentLocation
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class CurrentLocationListener(
    private val locationManager: LocationManager,
    private val task: Continuation<OneOf<Failure, CurrentLocation>>
) : LocationListener {
    override fun onLocationChanged(location: Location?) {
        val currentLocation = CurrentLocation(
            location?.latitude ?: 0.0,
            location?.longitude ?: 0.0
        )
        locationManager.removeUpdates(this)
        try {
            task.resume(OneOf.Success(currentLocation))
        } catch (e: Exception) {
            Log.d("CurrentLocationListener", "Resumed")
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {
        locationManager.removeUpdates(this)
        task.resume(OneOf.Error(Failure.GpsDisabled()))
    }

    fun unsubscribe() {
        locationManager.removeUpdates(this)
        try {
            task.resume(OneOf.Error(Failure.CancelFailure()))
        } catch (e: Exception) {
            Log.d("CurrentLocationListener", "Resumed")
        }
    }
}