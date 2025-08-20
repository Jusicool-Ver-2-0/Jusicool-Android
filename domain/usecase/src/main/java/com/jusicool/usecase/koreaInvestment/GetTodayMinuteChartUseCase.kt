package com.jusicool.usecase.koreaInvestment

import com.jusicool.entity.price.MinuteCandleEntity
import com.jusicool.repository.KoreaInvestmentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetTodayMinuteChartUseCase @Inject constructor(
    private val koreaInvestmentRepository: KoreaInvestmentRepository
) {
    operator fun invoke(
        inputIsCd: String,
        earliestTime: String, // ex: "090000"
        latestTime: String,   // ex: "153000"
        initialDelayMs: Long = 500L
    ): Flow<List<MinuteCandleEntity>> = flow {

        val formatter = DateTimeFormatter.ofPattern("HHmmss")
        var currentLocalTime = LocalTime.parse(earliestTime, formatter)
        val targetTime = LocalTime.parse(latestTime, formatter)

        val allCandles = mutableListOf<MinuteCandleEntity>()
        var trCont = ""

        while (shouldContinueRequest(currentLocalTime, targetTime)) {
            val currentTimeStr = currentLocalTime.plusMinutes(30).format(formatter)
            val nowTime = LocalTime.now()

            val candles = requestMinutePrice(inputIsCd, currentTimeStr, trCont)
                .filter { it.dateTime.toLocalTime() <= nowTime }

            if (candles.isNotEmpty()) {
                allCandles.addAll(candles)
            }

            currentLocalTime = currentLocalTime.plusMinutes(30)
            delay(initialDelayMs)

            emit(allCandles.sortedBy { it.dateTime })
        }
    }

    private fun shouldContinueRequest(
        currentLocalTime: LocalTime,
        targetTime: LocalTime
    ): Boolean {
        return currentLocalTime.plusMinutes(30) <= targetTime && currentLocalTime <= LocalTime.now()
    }

    private suspend fun requestMinutePrice(
        inputIsCd: String,
        inputHour1: String,
        trCont: String
    ): List<MinuteCandleEntity> {
        val candles = mutableListOf<MinuteCandleEntity>()

        koreaInvestmentRepository.getMinutePrice(
            inputIsCd = inputIsCd,
            inputHour1 = inputHour1,
            trCont = trCont
        ).collect { candleList ->
            candles.addAll(candleList)
        }

        return candles
    }
}
