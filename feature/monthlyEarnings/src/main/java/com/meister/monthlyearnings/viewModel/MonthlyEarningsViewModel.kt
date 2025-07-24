package com.meister.monthlyearnings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.usecase.order.GetMonthlyRateUseCase
import com.jusicool.utils.Logger
import com.jusicool.utils.retryPolicy
import com.meister.monthlyearnings.viewModel.uiState.MonthlyEarningsUiState
import com.meister.monthlyearnings.viewModel.uiState.MonthlyEarningsUiState.Companion.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MonthlyEarningsViewModel @Inject constructor(
    private val getMonthlyRateUseCase: GetMonthlyRateUseCase
) : ViewModel() {

    private val refreshTrigger = MutableSharedFlow<Unit>()

    fun refresh() {
        viewModelScope.launch {
            refreshTrigger.emit(Unit)
        }
    }

    val uiState: StateFlow<MonthlyEarningsUiState> =
        refreshTrigger
            .onStart { emit(Unit) }
            .flatMapLatest {
                getMonthlyRateUseCase()
                    .retryWhen(retryPolicy("MonthlyEarningsViewModel"))
                    .map(::success)
                    .catch { e ->
                        Logger.e(
                            "MonthlyEarningsViewModel",
                            "Error fetching holdings price at MonthlyEarningsViewModel.kt:35",
                            throwable = e
                        )
                        emit(MonthlyEarningsUiState(errorMessage = e.message))
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MonthlyEarningsUiState()
            )
}