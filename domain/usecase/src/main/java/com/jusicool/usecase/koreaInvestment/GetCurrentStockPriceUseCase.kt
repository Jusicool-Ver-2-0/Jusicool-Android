package com.jusicool.usecase.koreaInvestment

import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.repository.KoreaInvestmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentStockPriceUseCase @Inject constructor(
    private val koreaInvestmentRepository: KoreaInvestmentRepository,
) {
    operator fun invoke(
        markets: List<String>,
        delay: Long = 500L,
    ): Flow<List<AssetsCurrentPrice>> =
        koreaInvestmentRepository.getStockCurrentPrice(
            markets = markets,
            delay = delay
        )
}
