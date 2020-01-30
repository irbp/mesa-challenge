package com.italo.mesachallenge.data.di

import com.italo.mesachallenge.data.R
import com.italo.mesachallenge.data.network.api.PlacesApi
import com.italo.mesachallenge.data.network.source.PlaceNetworkDataSource
import com.italo.mesachallenge.data.network.source.PlaceNetworkDataSourceImpl
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val dataNetworkModule = module {
    factory {
        providesOkHttpClient()
    }
    single {
        createWebService<PlacesApi>(
            okHttpClient = get(),
            baseUrl = androidContext().getString(R.string.places_base_url)
        )
    }
    factory<PlaceNetworkDataSource> {
        PlaceNetworkDataSourceImpl(androidContext(), get())
    }
}

fun providesOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, baseUrl: String): T {
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
        .create(T::class.java)
}