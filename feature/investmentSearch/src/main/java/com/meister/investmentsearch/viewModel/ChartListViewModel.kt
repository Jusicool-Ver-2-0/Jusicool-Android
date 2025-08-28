package com.meister.investmentsearch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.usecase.market.GetMarketListWithCurrentPriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ChartListViewModel @Inject constructor(
    private val getMarketListWithCurrentPriceUseCase: GetMarketListWithCurrentPriceUseCase
) : ViewModel() {

    private var currentPage = 20
    private val pageSize = 40
    private var isLoadingMore = false
    private var isLastPage = false

    private val _uiState = MutableStateFlow(ChartListUiState())
    val uiState: StateFlow<ChartListUiState> = _uiState.asStateFlow()

    init {
        loadNextPage()
    }

    internal fun loadNextPage() = viewModelScope.launch {
        if (isLoadingMore || isLastPage) return@launch

        isLoadingMore = true
        getMarketListWithCurrentPriceUseCase(currentPage = currentPage, pageSize = pageSize)
            .onEach { newList ->
                val currentList = _uiState.value.chartListData.toList()
                val combinedList = currentList + newList
                _uiState.value = ChartListUiState(
                    isLoading = false,
                    chartListData = combinedList.toPersistentList()
                )
                currentPage++
                isLastPage = newList.size < pageSize
            }
            .catch { e ->
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
            .also { isLoadingMore = false }
    }
}