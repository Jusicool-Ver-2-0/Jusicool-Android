package com.jusicool.jusicool_android

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.jusicool.network.datasource.koreaInvestment.ws.KoreaInvestmentWebSocketManagerInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLifecycleObserver @Inject constructor(
    private val webSocketManager: KoreaInvestmentWebSocketManagerInterface
) : DefaultLifecycleObserver {

    override fun onStop(owner: LifecycleOwner) {
        webSocketManager.disconnect()
    }
}