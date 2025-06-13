package com.jusicool.repository.holding

import com.jusicool.entity.holding.HoldingModel
import kotlinx.coroutines.flow.Flow

interface HoldingRepository {
    fun getHolding(): Flow<List<HoldingModel>>
}