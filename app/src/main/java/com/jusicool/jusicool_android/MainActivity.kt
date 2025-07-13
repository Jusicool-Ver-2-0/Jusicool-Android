package com.jusicool.jusicool_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.ProcessLifecycleOwner
import com.jusicool.jusicool_android.ui.JusicoolApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appLifecycleObserver: AppLifecycleObserver

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)

        enableEdgeToEdge()
        setContent {
            JusicoolApp(windowSizeClass = calculateWindowSizeClass(activity = this))
        }
    }
}