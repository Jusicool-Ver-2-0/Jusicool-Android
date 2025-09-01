package com.jusicool.signup.viewModel.uiState

import com.jusicool.entity.school.SchoolInfoModel

sealed interface SearchSchoolUiState {
    object Loading : SearchSchoolUiState
    data class Success(val school: List<SchoolInfoModel>) : SearchSchoolUiState
    data class Error(val message: String) : SearchSchoolUiState
}