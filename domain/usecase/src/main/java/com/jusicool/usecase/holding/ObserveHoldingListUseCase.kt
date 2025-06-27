package com.jusicool.usecase.holding

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.repository.holding.HoldingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveHoldingListUseCase @Inject constructor(
    private val holdingRepository: HoldingRepository
) {
    operator fun invoke(): Flow<HoldingType> =
        holdingRepository.observeHoldings()
            .map { list ->
                val stockHoldings = list.filter { it.marketType == "STOCK" }
                val cryptoHoldings = list.filter { it.marketType == "CRYPTO" }

                HoldingType(
                    stockHoldings = stockHoldings,
                    cryptoHoldings = cryptoHoldings,
                )
            }
}

data class HoldingType(
    val stockHoldings: List<HoldingModel>,
    val cryptoHoldings: List<HoldingModel>
)