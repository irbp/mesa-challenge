package com.italo.domain.provider

import com.italo.domain.base.Failure
import com.italo.domain.base.OneOf
import com.italo.domain.model.CurrentLocation

interface LocationProvider {
    fun getLocation(): OneOf<Failure, CurrentLocation>
    fun cancel()
}