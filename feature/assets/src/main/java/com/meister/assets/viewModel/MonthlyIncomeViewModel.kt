package com.meister.assets.viewModel

import androidx.lifecycle.ViewModel
import com.meister.assets.viewModel.uiState.MonthlyIncomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MonthlyIncomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MonthlyIncomeUiState(isLoading = true))
    val uiState: StateFlow<MonthlyIncomeUiState> = _uiState.asStateFlow()
}