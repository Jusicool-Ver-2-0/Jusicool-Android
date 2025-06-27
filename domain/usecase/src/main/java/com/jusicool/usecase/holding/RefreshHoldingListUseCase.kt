package com.jusicool.usecase.holding

import com.jusicool.repository.holding.HoldingRepository
import javax.inject.Inject

class RefreshHoldingListUseCase @Inject constructor(
    private val holdingRepository: HoldingRepository,
) {
    suspend operator fun invoke(): Unit = holdingRepository.refreshHoldings()
}