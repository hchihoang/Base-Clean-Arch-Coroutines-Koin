package com.inetkr.base

import android.app.Application
import androidx.multidex.MultiDex
import com.inetkr.base.di.AppModule
import com.inetkr.base.di.NetworkModule
import com.inetkr.base.di.SharePrefModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(listOf(AppModule, NetworkModule, SharePrefModule))
        }
    }

}