package com.meister.orderhistory.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class OrderHistoryViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(OrderHistoryUiState())
    val uiState: StateFlow<OrderHistoryUiState> = _uiState.asStateFlow()
}