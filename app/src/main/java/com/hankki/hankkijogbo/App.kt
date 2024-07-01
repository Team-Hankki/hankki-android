package com.hankki.hankkijogbo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    private fun initTimber() {
        // if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
