package com.meister.investmentsearch.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meister.investmentsearch.viewModel.ChartListViewModel

@Composable
internal fun ChartListRoute(
    modifier: Modifier = Modifier,
    viewModel: ChartListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    when {
        uiState.isLoading -> {}
        uiState.errorMessage != null -> {}
        else -> {
            ChartListScreen(modifier = modifier)
        }
    }

}


@Composable
internal fun ChartListScreen(
    modifier: Modifier = Modifier,
) {

}


data class ChartItemData(
    val name: String,             // 이름 (예: "애플", "비트코인")
    val logoUrl: String?,         // 로고 URL 또는 리소스 ID (옵션)
    val priceChange: Int,         // 가격 변화 (예: +1111816)
    val percentageChange: Double  // 변화율 (예: 7.9)
)
