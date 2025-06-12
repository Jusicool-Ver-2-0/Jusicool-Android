package com.jusicool.network.datasource.holding

import com.jusicool.model.holding.HoldingResponse
import kotlinx.coroutines.flow.Flow

interface HoldingDataSource {
    fun getHolding(): Flow<List<HoldingResponse>>
}