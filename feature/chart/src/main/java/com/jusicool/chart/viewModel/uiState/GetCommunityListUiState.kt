package com.jusicool.chart.viewModel.uiState

import com.jusicool.entity.community.CommunityListModel

sealed interface GetCommunityListUiState {
    object Loading : GetCommunityListUiState
    object Blank : GetCommunityListUiState
    data class Success(val communityList: List<CommunityListModel>): GetCommunityListUiState
    data class Error(val message: String) : GetCommunityListUiState
}