package com.heixss.cats

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CatsApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}