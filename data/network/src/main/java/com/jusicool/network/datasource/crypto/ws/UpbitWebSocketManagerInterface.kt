package com.jusicool.network.datasource.crypto.ws

import com.jusicool.model.crypto.ws.UpbitTickerResponse
import kotlinx.coroutines.flow.StateFlow

interface UpbitWebSocketManagerInterface {
    val tickerListFlow: StateFlow<List<UpbitTickerResponse>>

    fun connect(markets: List<String>)

    fun disconnect()
}
