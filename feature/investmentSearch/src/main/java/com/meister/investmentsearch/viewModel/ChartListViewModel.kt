package com.meister.investmentsearch.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class ChartListViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ChartListUiState(isLoading = true))
    val uiState: StateFlow<ChartListUiState> = _uiState.asStateFlow()
}