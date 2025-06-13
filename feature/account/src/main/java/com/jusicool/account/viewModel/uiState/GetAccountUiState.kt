package com.jusicool.account.viewModel.uiState

import com.jusicool.entity.account.AccountModel

sealed interface GetAccountUiState {
    object Loading : GetAccountUiState
    data class Success(val account:  AccountModel) : GetAccountUiState
    data class Error(val message: String) : GetAccountUiState
}