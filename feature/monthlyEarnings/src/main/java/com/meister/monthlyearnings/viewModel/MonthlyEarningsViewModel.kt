package com.meister.monthlyearnings.viewModel

import androidx.lifecycle.ViewModel
import com.meister.monthlyearnings.viewModel.uiState.MonthlyEarningsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class MonthlyEarningsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MonthlyEarningsUiState())
    val uiState: StateFlow<MonthlyEarningsUiState> = _uiState.asStateFlow()
}