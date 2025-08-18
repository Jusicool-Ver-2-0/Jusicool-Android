package com.jusicool.usecase.holding

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.entity.market.MarketType
import com.jusicool.repository.HoldingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHoldingResponseUseCase @Inject constructor(
    private val holdingRepository: HoldingRepository
) {
    operator fun invoke(): Flow<HoldingType> {
        val holding = holdingRepository.getHolding()

        val stockHoldings = holding.map { list ->
            list.filter { it.market.marketType == MarketType.STOCK }
        }

        val cryptoHoldings = holding.map { list ->
            list.filter { it.market.marketType == MarketType.CRYPTO }
        }

        return combine(stockHoldings, cryptoHoldings) { stockHoldings, cryptoHoldings ->
            HoldingType(
                stockHoldings = stockHoldings,
                cryptoHoldings = cryptoHoldings
            )
        }
    }
}

data class HoldingType(
    val stockHoldings: List<HoldingModel>,
    val cryptoHoldings: List<HoldingModel>
)