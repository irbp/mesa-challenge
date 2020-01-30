package com.italo.domain.di

import com.italo.domain.usecases.GetCurrentLocationUseCase
import com.italo.domain.usecases.GetCurrentUserUseCase
import com.italo.domain.usecases.GetNearbyPlacesUseCase
import com.italo.domain.usecases.SaveCurrentUserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetCurrentLocationUseCase(locationProvider = get())
    }
    factory {
        GetCurrentUserUseCase(userRepository = get())
    }
    factory {
        SaveCurrentUserUseCase(userRepository = get())
    }
    factory {
        GetNearbyPlacesUseCase(placeRepository = get())
    }
}

val domainModule = listOf(useCaseModule)