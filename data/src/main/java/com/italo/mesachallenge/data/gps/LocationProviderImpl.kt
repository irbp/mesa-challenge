package com.italo.mesachallenge.data.gps

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.util.Log
import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.CurrentLocation
import com.italo.domain.provider.LocationProvider
import com.karumi.dexter.Dexter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.suspendCoroutine

class LocationProviderImpl(private val activity: Activity) : LocationProvider {

    private var locationManager: LocationManager? = null
    private var locationListener: CurrentLocationListener? = null

    override fun getLocation(): OneOf<Failure, CurrentLocation> = runBlocking {
        Log.d("LocationProvider", "Hi I'm here")
        val grantedResult = getLocationPermissions()
        Log.d("LocationProvider", "Hi I'm here")

        if (grantedResult.isError) {
            val error = (grantedResult as OneOf.Error<Failure>).error
            OneOf.Error(error)
        } else {
            getLocationFromGps()
        }
    }

    private suspend fun getLocationPermissions(): OneOf<Failure, Boolean> = suspendCoroutine {
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(PermissionCallback(it))
            .check()
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLocationFromGps(): OneOf<Failure, CurrentLocation> = suspendCoroutine {
        locationListener?.unsubscribe()
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager?.let { manager ->
            locationListener = CurrentLocationListener(manager, it)
            GlobalScope.launch(Dispatchers.Main) {
                manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    0.0f,
                    locationListener!!
                )
            }
        }
    }

//    private suspend fun trackUserPosition()

    override fun cancel() {
        locationListener?.unsubscribe()
        locationListener = null
        locationManager = null
    }
}