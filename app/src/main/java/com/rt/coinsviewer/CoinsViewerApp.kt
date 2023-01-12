package com.rt.coinsviewer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoinsViewerApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}