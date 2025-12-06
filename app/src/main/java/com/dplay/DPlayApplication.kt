package com.dplay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DPlayApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        setTimber()
    }

    private fun setTimber() {
        if (BuildConfig.DEBUG) Timber.Forest.plant(Timber.DebugTree())
    }
}
