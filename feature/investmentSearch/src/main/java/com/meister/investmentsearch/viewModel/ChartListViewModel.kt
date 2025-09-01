package com.meister.investmentsearch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.market.Market
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.usecase.market.GetMarketListUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ChartListViewModel @Inject constructor(
    private val getMarketListUseCase: GetMarketListUseCase
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 40
    }

    private var currentPage = 0
    private var isLastPage = false

    private val _pages = mutableListOf<List<RecommendMarketWithPrice>>()
    private val _marketPages = mutableListOf<List<Market>>()

    private val _uiState = MutableStateFlow(ChartListUiState())
    val uiState: StateFlow<ChartListUiState> = _uiState.asStateFlow()

    internal fun loadNextPage() {
        if (isLastPage) return

        viewModelScope.launch {
            getMarketListUseCase(currentPage = currentPage, pageSize = PAGE_SIZE)
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { e ->
                    Logger.e("ChartListViewModel", "❌ Error loading page $currentPage", e)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message,
                        )
                    }
                }
                .collect { newList ->
                    _marketPages.add(newList)

                    val combinedList = _pages.flatten().distinctBy { it.market }

                    _uiState.update { state ->
                        state.copy(
                            isInitialLoad = false,
                            chartListData = combinedList.toPersistentList()
                        )
                    }
                }
        }
    }

    internal fun setCurrentPage(page: Int) {
        currentPage = page
    }
}