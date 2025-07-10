package com.meister.investmentsearch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.usecase.market.GetTotalRecommendMarketListUseCase
import com.jusicool.utils.Logger
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
internal class ChartListViewModel @Inject constructor(
    getTotalRecommendMarketListUseCase: GetTotalRecommendMarketListUseCase
) : ViewModel() {
    val uiState: StateFlow<ChartListUiState> =
        getTotalRecommendMarketListUseCase()
            .map { recommendMarketList ->
                ChartListUiState(
                    isLoading = false,
                    chartListData = recommendMarketList.toPersistentList(),
                    errorMessage = null,
                )
            }
            .onEach { Logger.d("ChartListViewModel", it.toString()) }
            .catch { e ->
                Logger.e("ChartListViewModel", "Error fetching holdings price at ChartListViewModel.kt:22", e)
                emit(ChartListUiState(errorMessage = e.message))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = ChartListUiState()
            )
}