package com.jusicool.usecase.koreaInvestment

import com.jusicool.entity.koreaInvestment.AssetsCurrentPrice
import com.jusicool.repository.KoreaInvestmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentStockPriceUseCase @Inject constructor(
    private val koreaInvestmentRepository: KoreaInvestmentRepository,
) {
    operator fun invoke(
        markets: List<String>,
    ): Flow<List<AssetsCurrentPrice>> =
        koreaInvestmentRepository.getStockCurrentPrice(
            marketDivCode = "J",
            markets = markets
        )
}
