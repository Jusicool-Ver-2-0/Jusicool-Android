package com.jusicool.repository

import com.jusicool.entity.holding.HoldingModel
import kotlinx.coroutines.flow.Flow

interface HoldingRepository {
    fun getHolding(): Flow<List<HoldingModel>>
}