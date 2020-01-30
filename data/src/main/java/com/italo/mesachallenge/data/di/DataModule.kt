package com.italo.mesachallenge.data.di

import com.italo.domain.repository.PlaceRepository
import com.italo.domain.repository.UserRepository
import com.italo.mesachallenge.data.repository.PlaceRepositoryImpl
import com.italo.mesachallenge.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<UserRepository> {
        UserRepositoryImpl(cacheSource = get())
    }
    factory<PlaceRepository> {
        PlaceRepositoryImpl(networkSource = get())
    }
}

val dataModules = listOf(repositoryModule, dataCacheModule, dataNetworkModule)