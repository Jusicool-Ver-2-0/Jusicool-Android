package com.jusicool.chart.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StockSearchViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(StockSearchUiState(isLoading = true))
    val uiState: StateFlow<StockSearchUiState> = _uiState.asStateFlow()

    fun searchStock(searchText: String) {

    }

    fun onSearchTextChange(searchText: String) {

    }
}