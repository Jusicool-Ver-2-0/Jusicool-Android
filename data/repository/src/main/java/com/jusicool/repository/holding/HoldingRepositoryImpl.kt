package com.jusicool.repository.holding

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.model.mapper.holding.toModel
import com.jusicool.network.datasource.holding.HoldingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HoldingRepositoryImpl @Inject constructor(
    private val holdingDataSource: HoldingDataSource
): HoldingRepository{
    override fun getHolding(): Flow<List<HoldingModel>> {
        return holdingDataSource.getHolding().map { list -> list.map { it.toModel() } }
    }
}