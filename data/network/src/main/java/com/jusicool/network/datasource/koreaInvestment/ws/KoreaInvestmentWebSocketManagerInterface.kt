package com.jusicool.network.datasource.koreaInvestment.ws

import com.jusicool.model.koreaInvestment.ws.StockPriceSummary
import kotlinx.coroutines.flow.SharedFlow

interface KoreaInvestmentWebSocketManagerInterface {
    val stockTickerFlow: SharedFlow<StockPriceSummary>

    fun connect(approvalKey: String, trId: String, stockCode: String)
    fun disconnect()
}
