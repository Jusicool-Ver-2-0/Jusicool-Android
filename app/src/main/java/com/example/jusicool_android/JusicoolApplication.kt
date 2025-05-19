package com.example.jusicool_android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JusicoolApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}