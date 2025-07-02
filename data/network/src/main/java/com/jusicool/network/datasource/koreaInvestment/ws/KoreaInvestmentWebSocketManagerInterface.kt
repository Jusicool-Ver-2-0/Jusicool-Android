package com.jusicool.network.datasource.koreaInvestment.ws

import com.jusicool.model.koreaInvestment.ws.StockPriceSummary
import kotlinx.coroutines.flow.StateFlow

interface KoreaInvestmentWebSocketManagerInterface {
    val stockTickerFlow: StateFlow<StockPriceSummary?>

    fun connect(stockCode: String)
    fun disconnect()
}
