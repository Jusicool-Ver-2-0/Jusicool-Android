package com.jusicool.usecase.holding

import com.jusicool.repository.holding.HoldingRepository
import javax.inject.Inject

class GetHoldingResponseUseCase @Inject constructor(
    private val holdingRepository: HoldingRepository
) {
    operator fun invoke() = runCatching {
        holdingRepository.getHolding()
    }
}