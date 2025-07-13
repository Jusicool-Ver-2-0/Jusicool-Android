package com.meister.investmentsearch.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.usecase.market.SearchMarketWithPriceUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InvestmentSearchViewModel @Inject constructor(
    searchMarketWithPriceUseCase: SearchMarketWithPriceUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    internal val searchQuery = savedStateHandle.getStateFlow("searchQuery", "")

    internal val uiState: StateFlow<InvestmentSearchUiState> = searchQuery
        .debounce(300) // 빠른 타이핑 대응
        .distinctUntilChanged()
        .filter { it.isNotBlank() }
        .flatMapLatest { query ->
            searchMarketWithPriceUseCase(query)
                .map { marketWithPrice ->
                    InvestmentSearchUiState(
                        isLoading = false,
                        popularKeywordData = marketWithPrice.map { it.koreanName to it.currentPrice.toDouble() }.toPersistentList()
                    )
                }
                .onStart {
                    InvestmentSearchUiState(isLoading = true)
                }
                .catch { e ->
                    Logger.e("InvestmentSearchViewModel", "Error fetching search results", e)
                    InvestmentSearchUiState(isLoading = false, errorMessage = e.message)
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InvestmentSearchUiState()
        )

    internal fun onSearchTextChange(searchText: String) {
        savedStateHandle["searchQuery"] = searchText
    }
}