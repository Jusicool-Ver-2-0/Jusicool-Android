package com.jusicool.usecase.holding

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.repository.HoldingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHoldingResponseUseCase @Inject constructor(
    private val holdingRepository: HoldingRepository
) {
    operator fun invoke():Result<HoldingType> = runCatching {
        val holding = holdingRepository.getHolding()

        val stockHoldings = holding.map { list ->
            list.filter { it.marketType == "STOCK" }
        }

        val cryptoHoldings = holding.map { list ->
            list.filter { it.marketType == "CRYPTO" }
        }

        HoldingType(
            stockHoldings = stockHoldings,
            cryptoHoldings = cryptoHoldings
        )
    }
}

data class HoldingType(
    val stockHoldings: Flow<List<HoldingModel>>,
    val cryptoHoldings: Flow<List<HoldingModel>>
)