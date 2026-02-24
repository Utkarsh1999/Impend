package com.impend.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impend.shared.analytics.AnalyticsTransformer
import com.impend.shared.analytics.DataPoint
import com.impend.shared.domain.repository.ExpenseRepository
import com.impend.shared.domain.repository.PledgeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class ProgressUiState(
    val dailyTrend: List<DataPoint> = emptyList(),
    val moodCorrelation: List<DataPoint> = emptyList(),
    val pledgeMastery: Float = 0f,
    val isLoading: Boolean = true
)

class ProgressViewModel(
    private val expenseRepository: ExpenseRepository,
    private val pledgeRepository: PledgeRepository,
    private val transformer: AnalyticsTransformer
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            expenseRepository.getAllExpenses().collectLatest { expenses ->
                val dailyTrend = transformer.transformToDailyTrend(expenses)
                val moodCorrelation = transformer.transformToMoodCorrelation(expenses)
                
                val stats = pledgeRepository.getPledgeSummaryStats()
                val successful = (stats["COMPLETED"] ?: 0L).toInt()
                val total = stats.values.sum().toInt()
                val mastery = transformer.calculatePledgeMastery(total, successful)
                
                _uiState.value = ProgressUiState(
                    dailyTrend = dailyTrend,
                    moodCorrelation = moodCorrelation,
                    pledgeMastery = mastery,
                    isLoading = false
                )
            }
        }
    }
}
