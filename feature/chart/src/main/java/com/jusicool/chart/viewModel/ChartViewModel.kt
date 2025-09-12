package com.jusicool.chart.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.chart.viewModel.uiState.GetCommunityListUiState
import com.jusicool.chart.viewModel.uiState.GetCurrentMinuteCandleUiState
import com.jusicool.chart.viewModel.uiState.GetMinuteCandleUiState
import com.jusicool.entity.price.MinuteCandleEntity
import com.jusicool.usecase.community.GetCommunityListUseCase
import com.jusicool.usecase.crypto.GetCurrentCryptoMinuteCandleUseCase
import com.jusicool.usecase.crypto.GetMinuteCandleUseCase
import com.jusicool.usecase.koreaInvestment.GetCurrentStockMinuteChartUseCase
import com.jusicool.usecase.koreaInvestment.GetCurrentStockPriceUseCase
import com.jusicool.usecase.koreaInvestment.ObserveRealtimeStockPriceUseCase
import com.jusicool.utils.Logger
import com.jusicool.utils.isStockMarketOpen
import com.jusicool.utils.isValidCryptoMarketCode
import com.jusicool.utils.isValidStockMarketCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
internal class ChartViewModel @Inject constructor(
    private val getMinuteCandleUseCase: GetMinuteCandleUseCase,
    private val getCurrentCryptoMinuteCandleUseCase: GetCurrentCryptoMinuteCandleUseCase,
    private val observeRealtimeStockPriceUseCase: ObserveRealtimeStockPriceUseCase,
    private val getCurrentStockMinuteChartUseCase: GetCurrentStockMinuteChartUseCase,
    private val getCurrentStockPriceUseCase: GetCurrentStockPriceUseCase,
    private val getCommunityListUseCase: GetCommunityListUseCase
) : ViewModel() {
    private val _minuteCandleUiState =
        MutableStateFlow<GetMinuteCandleUiState>(GetMinuteCandleUiState.Loading)
    val minuteCandleUiState = _minuteCandleUiState.asStateFlow()

    private val _communityListUiState =
        MutableStateFlow<GetCommunityListUiState>(GetCommunityListUiState.Loading)
    val communityListUiState = _communityListUiState.asStateFlow()

    private val _markets = MutableStateFlow<String?>(null)

    private var oldestDate: LocalDateTime? = null

    val getCurrentMinuteCandleUiState: StateFlow<GetCurrentMinuteCandleUiState> =
        _markets.flatMapLatest { market ->
            if (market.isNullOrBlank()) {
                flowOf<GetCurrentMinuteCandleUiState>(GetCurrentMinuteCandleUiState.Blank)
            } else {
                when {
                    market.isValidCryptoMarketCode() ->
                        flow {
                            while (currentCoroutineContext().isActive) {
                                getCurrentCryptoMinuteCandleUseCase(market)
                                    .map { data ->
                                        Logger.d("ChartViewModel", "가격 로딩 중 성공: $data")
                                        GetCurrentMinuteCandleUiState.Success(data)
                                    }
                                    .catch { e ->
                                        Logger.e("ChartViewModel", "가격 로딩 중 에러: ${e.message}")
                                        emit(
                                            GetCurrentMinuteCandleUiState.Error(
                                                e.message ?: "Unknown error"
                                            )
                                        )
                                    }
                                    .collect { emit(it) }

                                delay(500) // 0.5초마다 반복
                            }
                        }

                    market.isValidStockMarketCode() ->
                        flow {
                            if (isStockMarketOpen()) {
                                observeRealtimeStockPriceUseCase(stockCodes = listOf(market))
                                    .map { data ->
                                        Logger.d("ChartViewModel", "가격 로딩 중 성공: $data")
                                        GetCurrentMinuteCandleUiState.Success(data.map {
                                            MinuteCandleEntity(
                                                dateTime = LocalDateTime.now(),
                                                openPrice = it.currentPrice,
                                                highPrice = it.currentPrice,
                                                lowPrice = it.currentPrice,
                                                closePrice = it.currentPrice,
                                                volume = 0.0
                                            )
                                        })
                                    }
                                    .catch { e ->
                                        Logger.e("ChartViewModel", "가격 로딩 중 에러: ${e.message}")
                                        emit(
                                            GetCurrentMinuteCandleUiState.Error(
                                                e.message ?: "Unknown error"
                                            )
                                        )
                                    }.collect { emit(it) }
                            } else {
                                while (currentCoroutineContext().isActive) {
                                    getCurrentStockPriceUseCase(markets = listOf(market))
                                        .map { data ->
                                            Logger.d("ChartViewModel", "가격 로딩 중 성공: $data")
                                            GetCurrentMinuteCandleUiState.Success(data.map {
                                                MinuteCandleEntity(
                                                    dateTime = LocalDateTime.now(),
                                                    openPrice = it.currentPrice,
                                                    highPrice = it.currentPrice,
                                                    lowPrice = it.currentPrice,
                                                    closePrice = it.currentPrice,
                                                    volume = 0.0
                                                )
                                            })
                                        }
                                        .catch { e ->
                                            Logger.e("ChartViewModel", "가격 로딩 중 에러: ${e.message}")
                                            emit(
                                                GetCurrentMinuteCandleUiState.Error(
                                                    e.message ?: "Unknown error"
                                                )
                                            )
                                        }.collect { emit(it) }

                                    delay(500) // 0.5초마다 반복
                                }
                            }
                        }

                    else -> flowOf()
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GetCurrentMinuteCandleUiState.Loading
        )


    fun getMarkets(market: String) {
        _markets.value = market
    }

    fun getMinuteCandle(market: String, to: String, count: Int) = viewModelScope.launch {
        when {
            market.isValidCryptoMarketCode() -> {
                getMinuteCandleUseCase(market = market, to = to, count = count)
                    .catch { e ->
                        Logger.e("ChartViewModel", "Crypto 캔들 오류", e)
                        _minuteCandleUiState.value =
                            GetMinuteCandleUiState.Error(e.message ?: "Unknown error")
                    }
                    .collect { candleList ->
                        mergeAndEmitCandles(candleList)
                    }
            }

            market.isValidStockMarketCode() -> {
                getCurrentStockMinuteChartUseCase(
                    inputIsCd = market,
                    earliestTime = "090000",
                    latestTime = "153000"
                )
                    .catch { e ->
                        Logger.e("ChartViewModel", "Stock 캔들 오류", e)
                        _minuteCandleUiState.value =
                            GetMinuteCandleUiState.Error(e.message ?: "Unknown error")
                    }
                    .collect { candleList ->
                        mergeAndEmitCandles(candleList)
                    }
            }

            else -> {
                Logger.e("ChartViewModel", "지원하지 않는 마켓 코드: $market")
            }
        }
    }

    private fun mergeAndEmitCandles(newCandles: List<MinuteCandleEntity>) {
        val reversed = newCandles.asReversed()
        val existingCandles =
            (_minuteCandleUiState.value as? GetMinuteCandleUiState.Success)?.chart
                ?: emptyList()

        val combined = (existingCandles + reversed)
            .distinctBy { it.dateTime }
            .sortedBy { it.dateTime }

        _minuteCandleUiState.value = GetMinuteCandleUiState.Success(combined)
        Logger.d("ChartViewModel", "총 ${combined.size}개 캔들 반영됨")
    }


    fun startPeriodicRequest(market: String) = viewModelScope.launch {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        while (isActive) {
            val now = System.currentTimeMillis()
            val nextMinute = (now / 60000 + 1) * 60000
            val delayMillis = nextMinute - now

            delay(delayMillis)
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
            val toTime = formatter.format(calendar.time)

            getMinuteCandle(market = market, to = toTime, count = 1)
        }
    }

    fun refreshCandleData(market: String) {
        val existingCandles =
            (_minuteCandleUiState.value as? GetMinuteCandleUiState.Success)?.chart
                ?: emptyList()

        val baseOldestTime = oldestDate ?: existingCandles.minOfOrNull { it.dateTime }
        ?: LocalDateTime.now()

        val toTime = baseOldestTime.minusMinutes(200)
        oldestDate = toTime

        Logger.d("ChartViewModel", "새로고침 기준: $baseOldestTime → 요청 to: $toTime")

        viewModelScope.launch {
            when {
                market.isValidCryptoMarketCode() -> {
                    getMinuteCandleUseCase(market = market, to = toTime.toString(), count = 200)
                        .catch { e ->
                            Logger.e("ChartViewModel", "Crypto 새로고침 실패", e)
                            _minuteCandleUiState.value =
                                GetMinuteCandleUiState.Error(e.message ?: "Unknown error")
                        }
                        .collect { mergeAndEmitCandles(it) }
                }

                market.isValidStockMarketCode() -> {
                    getCurrentStockMinuteChartUseCase(
                        inputIsCd = market,
                        earliestTime = "090000",
                        latestTime = "153000"
                    )
                        .catch { e ->
                            Logger.e("ChartViewModel", "Stock 새로고침 실패", e)
                            _minuteCandleUiState.value =
                                GetMinuteCandleUiState.Error(e.message ?: "Unknown error")
                        }
                        .collect { mergeAndEmitCandles(it) }
                }
            }
        }

        fun getCommunityList(market: String) {
            viewModelScope.launch {
                getCommunityListUseCase(market = market)
                    .catch { e ->
                        Logger.d("ChartViewModel", "커뮤니티 리스트 가져오기 실패: ${e.message}")
                        _communityListUiState.value =
                            GetCommunityListUiState.Error(e.message ?: "Unknown error")
                    }
                    .collect { data ->
                        Logger.d("ChartViewModel", "커뮤니티 리스트: ${data}")
                        _communityListUiState.value = GetCommunityListUiState.Success(data)
                    }
            }
        }
    }
}