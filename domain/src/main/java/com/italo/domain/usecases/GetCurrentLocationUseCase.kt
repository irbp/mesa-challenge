package com.italo.domain.usecases

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.base.UseCase
import com.italo.domain.model.CurrentLocation
import com.italo.domain.provider.LocationProvider

class GetCurrentLocationUseCase(private val locationProvider: LocationProvider) :
    UseCase<CurrentLocation, UseCase.NoParams>() {

    override suspend fun run(params: NoParams): OneOf<Failure, CurrentLocation> =
        locationProvider.getLocation()

    override fun cancel() {
        super.cancel()
        locationProvider.cancel()
    }
}