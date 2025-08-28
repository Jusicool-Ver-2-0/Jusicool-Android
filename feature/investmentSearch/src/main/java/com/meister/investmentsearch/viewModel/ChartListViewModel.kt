package com.meister.investmentsearch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.usecase.market.GetMarketListWithCurrentPriceUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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

    internal fun loadNextPage() {
        if (isLoadingMore || isLastPage) {
            Logger.d(
                "ChartListViewModel",
                "loadNextPage skipped. isLoadingMore=$isLoadingMore, isLastPage=$isLastPage"
            )
            return
        }

        Logger.d("ChartListViewModel", "▶️ Start loading page $currentPage")

        getMarketListWithCurrentPriceUseCase(currentPage = currentPage, pageSize = pageSize)
            .onStart {
                isLoadingMore = true
                _uiState.update { it.copy(isLoading = true, isPaging = true) }
            }
            .onEach { newList ->
                Logger.d("ChartListViewModel", "✅ Page $currentPage loaded. Items=${newList.size}")

                _uiState.update { state ->
                    val combinedList = state.chartListData + newList
                    state.copy(
                        isLoading = false,
                        isPaging = false,
                        chartListData = combinedList.toPersistentList(),
                    )
                }

                Logger.d(
                    "ChartListViewModel",
                    "📊 CombinedList size=${_uiState.value.chartListData.size}, " +
                            "isLastPage=${newList.size < pageSize}"
                )

                currentPage++
                isLastPage = newList.size < pageSize
            }
            .catch { e ->
                Logger.e("ChartListViewModel", "❌ Error loading page $currentPage", e)
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
                Logger.d("ChartListViewModel", "🏁 End loading page $currentPage")
            }
            .launchIn(viewModelScope)
    }
}