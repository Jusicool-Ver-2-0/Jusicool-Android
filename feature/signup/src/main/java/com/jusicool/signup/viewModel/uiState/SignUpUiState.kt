package com.jusicool.signup.viewModel.uiState

sealed interface SignUpUiState {
    object Loading : SignUpUiState
    object Success : SignUpUiState
    data class Error(val message: String) : SignUpUiState
}