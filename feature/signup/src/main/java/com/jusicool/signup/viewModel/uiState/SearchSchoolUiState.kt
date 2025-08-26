package com.jusicool.signup.viewModel.uiState

sealed interface SearchSchoolUiState {
    object Loading : SearchSchoolUiState
    object Success : SearchSchoolUiState
    data class Error(val message: String) : SearchSchoolUiState
}