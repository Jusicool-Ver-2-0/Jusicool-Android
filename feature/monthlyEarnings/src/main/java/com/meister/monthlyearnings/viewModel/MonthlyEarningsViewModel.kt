package com.meister.monthlyearnings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.usecase.order.GetMonthlyRateUseCase
import com.jusicool.utils.Logger
import com.meister.monthlyearnings.viewModel.uiState.MonthlyEarningsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class MonthlyEarningsViewModel @Inject constructor(
    private val getMonthlyRateUseCase: GetMonthlyRateUseCase
) : ViewModel() {

    val uiState: StateFlow<MonthlyEarningsUiState> = getMonthlyRateUseCase()
        .retryWhen { cause, attempt ->
            Logger.e("MonthlyEarningsViewModel", "Retry attempt $attempt due to $cause")

            if (attempt < 3) {
                kotlinx.coroutines.delay(3000)
                true
            } else {
                false
            }
        }
        .map { result ->
            MonthlyEarningsUiState(
                isLoading = false,
                monthlyEarnings = result.all.monthlyProfit(),
                monthlyReturnRate = result.all.monthlyRate,
                totalHoldingAssetsData = result.all.dailyRates.toPersistentList(),
                stockHoldingsData = result.stock.dailyRates.toPersistentList(),
                cryptoHoldingsData = result.crypto.dailyRates.toPersistentList(),
                errorMessage = null
            )
        }
        .catch { e ->
            Logger.e(
                "MonthlyEarningsViewModel",
                "Error fetching holdings price at AccountViewModel.kt:24",
                e
            )
            emit(MonthlyEarningsUiState(errorMessage = e.message))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MonthlyEarningsUiState()
        )

}