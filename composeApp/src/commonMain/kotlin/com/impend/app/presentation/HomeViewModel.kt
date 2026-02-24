package com.impend.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impend.shared.analytics.BehavioralAnalyticsEngine
import com.impend.shared.analytics.ImpulseRiskEngine
import com.impend.shared.analytics.InsightGenerator
import com.impend.shared.domain.model.Expense
import com.impend.shared.domain.model.Insight
import com.impend.shared.domain.model.Circle
import com.impend.shared.domain.usecase.AddExpenseUseCase
import com.impend.shared.domain.repository.CircleRepository
import com.impend.shared.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import com.impend.app.util.currentTimeMillis
import com.impend.shared.domain.repository.ExpenseRepository

class HomeViewModel(
    private val expenseRepository: ExpenseRepository,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val riskEngine: ImpulseRiskEngine,
    private val analyticsEngine: BehavioralAnalyticsEngine,
    private val insightGenerator: InsightGenerator,
    private val circleRepository: CircleRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                expenseRepository.getAllExpenses(),
                circleRepository.getJoinedCircles(),
                preferencesRepository.currencyCode
            ) { expenses, joinedCircles, currentCurrency ->
                val total = expenses.sumOf { it.amount }
                val avgMood = if (expenses.isNotEmpty()) expenses.mapNotNull { it.moodScore }.average().takeIf { !it.isNaN() } ?: 3.0 else 3.0
                
                // Calculate risk for last 5 expenses
                val lastExpenses = expenses.take(5)
                val avgRisk = if (lastExpenses.isNotEmpty()) {
                    lastExpenses.map { 
                        riskEngine.calculateRiskScore(expenses.filter { h -> h.id != it.id }, it) 
                    }.average()
                } else 0.0

                val moodCorrelation = analyticsEngine.calculateMoodSpendingCorrelation(expenses)
                
                val insights = insightGenerator.generateInsights(
                    avgImpulseRisk = avgRisk,
                    moodCorrelation = moodCorrelation,
                    avgMoodScore = avgMood
                )

                HomeUiState.Success(
                    expenses = expenses,
                    totalSpending = total,
                    avgMoodScore = avgMood,
                    avgImpulseRisk = avgRisk,
                    insights = insights,
                    joinedCircles = joinedCircles,
                    currencyCode = currentCurrency
                )
            }.collectLatest {
                _uiState.value = it
            }
        }
    }

    fun onAddExpenseClicked(amount: Double, category: String, moodScore: Int? = null) {
        viewModelScope.launch {
            addExpenseUseCase(
                amount = amount,
                category = category,
                dateMillis = currentTimeMillis(),
                moodScore = moodScore
            )
        }
    }

    fun onDeleteExpenseClicked(id: Long) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(id)
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val expenses: List<Expense>,
        val totalSpending: Double,
        val avgMoodScore: Double,
        val avgImpulseRisk: Double,
        val insights: List<Insight>,
        val joinedCircles: List<Circle> = emptyList(),
        val currencyCode: String
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
