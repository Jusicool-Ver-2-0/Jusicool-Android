package com.jusicool.jusicool_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ProcessLifecycleOwner
import com.jusicool.account.navigation.accountRoute
import com.jusicool.jusicool_android.ui.JusicoolApp
import com.jusicool.signin.navigation.signInRoute
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appLifecycleObserver: AppLifecycleObserver

    private val viewModel: AppViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)

        installSplashScreen().setKeepOnScreenCondition {
            viewModel.appLoginState.value is AppLoginState.Loading
        }

        setContent {
            val startDestination = if (viewModel.appLoginState.value is AppLoginState.Success) accountRoute else signInRoute

            JusicoolApp(
                windowSizeClass = calculateWindowSizeClass(this),
                startDestination = startDestination
            )
        }
    }
}
