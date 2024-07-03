package com.hankki.hankkijogbo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.hankki.core.network.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        setDarkMode()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // status bar color를 위해서 설정
        // statusbar color를 바꾸는 속성을 찾으면 수정 예정
    }
}
