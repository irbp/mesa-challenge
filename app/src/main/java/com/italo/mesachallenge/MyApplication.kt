package com.italo.mesachallenge

import android.app.Application
import com.italo.domain.di.domainModule
import com.italo.mesachallenge.data.di.dataModules
import com.italo.mesachallenge.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(domainModule + dataModules + listOf(presentationModule))
        }
    }

}