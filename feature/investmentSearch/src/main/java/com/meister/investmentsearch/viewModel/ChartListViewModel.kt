package com.meister.investmentsearch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.usecase.market.GetMarketListWithCurrentPriceUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ChartListViewModel @Inject constructor(
    private val getMarketListWithCurrentPriceUseCase: GetMarketListWithCurrentPriceUseCase
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 40
    }

    private var currentPage = 0
    private var isLoadingMore = false
    private var isLastPage = false

    private val _pages = mutableListOf<List<RecommendMarketWithPrice>>()

    private val _uiState = MutableStateFlow(ChartListUiState())
    val uiState: StateFlow<ChartListUiState> = _uiState.asStateFlow()

    init {
        loadNextPage()
    }

    internal fun loadNextPage() {
        if (isLoadingMore || isLastPage) return
        isLoadingMore = true

        val pageIndex = currentPage
        currentPage++

        viewModelScope.launch {
            getMarketListWithCurrentPriceUseCase(currentPage = pageIndex, pageSize = PAGE_SIZE)
                .onStart {
                    _uiState.update { it.copy(isLoading = true, isPaging = true) }
                }
                .catch { e ->
                    Logger.e("ChartListViewModel", "❌ Error loading page $pageIndex", e)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isPaging = false,
                            errorMessage = e.message
                        )
                    }
                }
                .onCompletion {
                    isLoadingMore = false
                }
                .collect { newList ->
                    _pages.add(newList)
                    val combinedList = _pages.flatten().distinctBy { it.market }
                    _uiState.update { it.copy(chartListData = combinedList.toPersistentList()) }


                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isPaging = false,
                            chartListData = combinedList.toPersistentList()
                        )
                    }

                    isLastPage = newList.size < PAGE_SIZE
                }
        }
    }

    internal fun setCurrentPage(page: Int) {
        currentPage = page
    }
}