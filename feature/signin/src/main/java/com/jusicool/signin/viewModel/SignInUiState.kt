package com.jusicool.signin.viewModel

sealed interface SignInUiState {
    object Loading : SignInUiState
    object Success : SignInUiState
    data class Error(val message: String) : SignInUiState
}