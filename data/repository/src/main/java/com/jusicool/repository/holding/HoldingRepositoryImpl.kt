package com.jusicool.repository.holding

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.local.dao.HoldingDao
import com.jusicool.local.dao.MarketDao
import com.jusicool.local.util.mapper.toDomain.toModel
import com.jusicool.local.util.mapper.toLocal.toEntities
import com.jusicool.network.datasource.holding.HoldingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HoldingRepositoryImpl @Inject constructor(
    private val holdingDataSource: HoldingDataSource,
    private val holdingDao: HoldingDao,
    private val marketDao: MarketDao,
) : HoldingRepository {

    override fun observeHoldings(): Flow<List<HoldingModel>> {
        return holdingDao.observeAllHoldingsWithMarket()
            .map { list -> list.map { it.toModel() } }
    }

    override suspend fun refreshHoldings() {
        holdingDataSource.getHolding()
            .collect { holdingResponseList ->
                holdingResponseList.forEach { holdingResponse ->
                    val (holdingEntity, marketEntity) = holdingResponse.toEntities()

                    marketDao.insertMarket(marketEntity)
                    holdingDao.insertHolding(holdingEntity)
                }
            }
    }
}
