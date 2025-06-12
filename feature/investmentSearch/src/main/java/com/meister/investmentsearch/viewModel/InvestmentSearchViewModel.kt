package com.meister.investmentsearch.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InvestmentSearchViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(InvestmentSearchUiState(isLoading = true))
    val uiState: StateFlow<InvestmentSearchUiState> = _uiState.asStateFlow()

    fun searchInvestment(searchText: String) {

    }

    fun onSearchTextChange(searchText: String) {

    }
}