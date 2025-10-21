package com.dplay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DPlayApplication : Application() {
    override fun onCreate(){
        super.onCreate()
    }
}