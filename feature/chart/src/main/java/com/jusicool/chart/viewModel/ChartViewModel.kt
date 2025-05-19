    package com.jusicool.chart.viewModel
    
    import android.util.Log
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.jusicool.usecase.chart.GetChartUseCase
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.launch
    import javax.inject.Inject
    
    @HiltViewModel
    class ChartViewModel @Inject constructor(
        private val getChartUseCase: GetChartUseCase
    ) : ViewModel() {
    
        private val _chartUiState = MutableStateFlow<ChartUiState>(ChartUiState.Loading)
        internal val chartUiState = _chartUiState.asStateFlow()
    
        init {
            getChart()
        }
    
        private fun getChart(
    
        ) = viewModelScope.launch {
                getChartUseCase().onSuccess { chartFlow ->
                    chartFlow.collect { chartList ->
                        Log.d("ChartViewModel", "받은 차트 리스트: $chartList")
                        _chartUiState.value = ChartUiState.Success(chartList)
                    }
                }
                    .onFailure { error ->
                        Log.e("ChartViewModel", "에러 발생: ${error.message}", error)
                        _chartUiState.value = ChartUiState.Error(error.message ?: "Unknown error")
                    }
            }
    }