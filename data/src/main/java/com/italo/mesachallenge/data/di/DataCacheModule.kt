package com.italo.mesachallenge.data.di

import com.italo.mesachallenge.data.cache.source.UserCacheDataSource
import com.italo.mesachallenge.data.cache.source.UserCacheDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataCacheModule = module {
    factory<UserCacheDataSource> {
        UserCacheDataSourceImpl(androidContext())
    }
}