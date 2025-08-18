package com.jusicool.chart.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.chart.viewModel.uiState.GetCurrentMinuteCandleUiState
import com.jusicool.chart.viewModel.uiState.GetMinuteCandleUiState
import com.jusicool.usecase.crypto.GetCurrentMinuteCandleUseCase
import com.jusicool.usecase.crypto.GetMinuteCandleUseCase
import com.jusicool.utils.Logger
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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
internal class CandleChartViewModel @Inject constructor(
    private val getMinuteCandleUseCase: GetMinuteCandleUseCase,
    private val getCurrentMinuteCandleUseCase: GetCurrentMinuteCandleUseCase
) : ViewModel() {
    private val _minuteCandleUiState = MutableStateFlow<GetMinuteCandleUiState>(GetMinuteCandleUiState.Loading)
    val minuteCandleUiState = _minuteCandleUiState.asStateFlow()

    private val _markets = MutableStateFlow<String?>(null)

    private var oldestDate: Date? = null

    val getCurrentMinuteCandleUiState: StateFlow<GetCurrentMinuteCandleUiState> =
        _markets.flatMapLatest { market ->
            if (market.isNullOrBlank()) {
                flowOf<GetCurrentMinuteCandleUiState>(GetCurrentMinuteCandleUiState.Blank)
            } else {
                flow {
                    while (currentCoroutineContext().isActive) {
                        getCurrentMinuteCandleUseCase(market)
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
        getMinuteCandleUseCase(market = market, to = to, count = count)
            .catch { e ->
                Logger.e("ChartViewModel", "Flow 내부 오류 발생", e)
                _minuteCandleUiState.value =
                    GetMinuteCandleUiState.Error(e.message ?: "Unknown error")
            }
            .collect { candleList ->

                val newCandles = candleList.asReversed()
                val existingCandles =
                    (_minuteCandleUiState.value as? GetMinuteCandleUiState.Success)?.chart
                        ?: emptyList()

                val combined = (existingCandles + newCandles)
                    .distinctBy { it.candleDateTimeKst }
                    .sortedBy { it.candleDateTimeKst }

                _minuteCandleUiState.value = GetMinuteCandleUiState.Success(combined)

                Logger.d("ChartViewModel", "Flow collect 성공: ${combined.size} candles")
            }
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
        val formatterWithTZ = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val formatterWithoutTZ = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        formatterWithTZ.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        formatterWithoutTZ.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        val existingCandles = (_minuteCandleUiState.value as? GetMinuteCandleUiState.Success)?.chart ?: emptyList()

        val baseOldestTime = oldestDate?.let { formatterWithTZ.format(it) } ?: run {
            existingCandles.minOfOrNull { it.candleDateTimeKst } ?: formatterWithTZ.format(Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).time)
        }

        val baseOldestDate = try {
            formatterWithTZ.parse(baseOldestTime)
        } catch (e: ParseException) {
            formatterWithoutTZ.parse(baseOldestTime)
        } ?: Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).time

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).apply {
            time = baseOldestDate
            add(Calendar.MINUTE, -200)
        }

        val toTime = formatterWithTZ.format(calendar.time)
        oldestDate = calendar.time

        Logger.d("ChartViewModel", "새로고침 마지막 시간: $baseOldestTime, 다음 요청 시간: $toTime")

        viewModelScope.launch {
            getMinuteCandleUseCase(market = market, to = toTime, count = 200)
                .catch { e ->
                    Logger.e("ChartViewModel", "캔들 새로고침 실패: ${e.message}")
                    _minuteCandleUiState.value = GetMinuteCandleUiState.Error(e.message ?: "Unknown error")
                }
                .collect { newCandles ->
                    Logger.d("ChartViewModel", "새로고침으로 $newCandles")
                    val reversedNewCandles = newCandles.asReversed()
                    val existingCandles =
                        (_minuteCandleUiState.value as? GetMinuteCandleUiState.Success)?.chart
                            ?: emptyList()

                    val combined = (existingCandles + reversedNewCandles)
                        .distinctBy { it.candleDateTimeKst }
                        .sortedBy { it.candleDateTimeKst }

                    _minuteCandleUiState.value = GetMinuteCandleUiState.Success(combined)
                    Logger.d(
                        "ChartViewModel",
                        "새로고침으로 ${newCandles.size}개 추가, 총 ${combined.size}개"
                    )
                }
        }
    }

}