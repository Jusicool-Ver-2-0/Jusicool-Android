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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class MonthlyEarningsViewModel @Inject constructor(
    private val getMonthlyRateUseCase: GetMonthlyRateUseCase
) : ViewModel() {

    val uiState: StateFlow<MonthlyEarningsUiState> = getMonthlyRateUseCase()
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
        .onEach { Logger.d("AccountViewModel", it.toString()) }
        .catch { e ->
            Logger.e(
                "AccountViewModel",
                "Error fetching holdings price at AccountViewModel.kt:53",
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