package com.jusicool.network.datasource.holding

import com.jusicool.model.holding.HoldingResponse
import com.jusicool.network.api.HoldingApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HoldingDataSourceImpl @Inject constructor(
    private val holdingApi: HoldingApi
): HoldingDataSource {
    override fun getHolding(): Flow<List<HoldingResponse>> =
        performApiRequest { holdingApi.getHolding() }
}