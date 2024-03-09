package com.example.taipeitravel

import android.app.Application
import timber.log.Timber

class App: Application() {
    val appContainer = AppContainer()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}