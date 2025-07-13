package com.jusicool.network.datasource.koreaInvestment.ws

import com.jusicool.model.koreaInvestment.ws.StockPriceSummary
import kotlinx.coroutines.flow.StateFlow

interface KoreaInvestmentWebSocketManagerInterface {
    val stockTickerMapFlow: StateFlow<List<StockPriceSummary>>

    fun connect(stockCodes: List<String>)
    fun disconnect()
}
