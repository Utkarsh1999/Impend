package com.impend.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impend.shared.analytics.ResilienceEngine
import com.impend.shared.domain.model.Expense
import com.impend.shared.domain.model.Pledge
import com.impend.shared.domain.repository.ExpenseRepository
import com.impend.shared.domain.repository.PledgeRepository
import com.impend.shared.domain.usecase.AddExpenseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.impend.app.util.currentTimeMillis

sealed class AddExpenseUiState {
    object Idle : AddExpenseUiState()
    data class InterceptionRequired(val prompt: String) : AddExpenseUiState()
    object Processing : AddExpenseUiState()
    object Success : AddExpenseUiState()
    data class Error(val message: String) : AddExpenseUiState()
}

class AddExpenseViewModel(
    private val expenseRepository: ExpenseRepository,
    private val pledgeRepository: PledgeRepository,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val resilienceEngine: ResilienceEngine
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddExpenseUiState>(AddExpenseUiState.Idle)
    val uiState: StateFlow<AddExpenseUiState> = _uiState

    private val _activePledge = MutableStateFlow<Pledge?>(null)
    val activePledge: StateFlow<Pledge?> = _activePledge

    private var pendingExpense: PendingExpense? = null

    fun resetState() {
        _uiState.value = AddExpenseUiState.Idle
    }

    fun validateAndSaveExpense(
        amount: Double,
        category: String,
        moodScore: Int? = null
    ) {
        viewModelScope.launch {
            _uiState.value = AddExpenseUiState.Processing
            
            val expenses = expenseRepository.getAllExpenses().first()
            val tempExpense = Expense(0, amount, category, currentTimeMillis(), null, moodScore)
            
            if (resilienceEngine.shouldIntervene(expenses, tempExpense)) {
                pendingExpense = PendingExpense(amount, category, moodScore)
                _uiState.value = AddExpenseUiState.InterceptionRequired(resilienceEngine.getRandomPrompt())
            } else {
                saveExpense(amount, category, moodScore)
            }
        }
    }

    fun proceedWithPendingExpense() {
        val pending = pendingExpense ?: return
        viewModelScope.launch {
            saveExpense(
                pending.amount,
                pending.category,
                pending.moodScore,
                customTag = "reframed:true"
            )
            pendingExpense = null
        }
    }

    fun cancelPendingExpense() {
        pendingExpense = null
        _uiState.value = AddExpenseUiState.Idle
    }

    private suspend fun saveExpense(
        amount: Double,
        category: String,
        moodScore: Int?,
        customTag: String? = null
    ) {
        try {
            addExpenseUseCase(
                amount = amount,
                category = category,
                dateMillis = currentTimeMillis(),
                moodScore = moodScore,
                customTag = customTag
            )
            _uiState.value = AddExpenseUiState.Success
        } catch (e: Exception) {
            _uiState.value = AddExpenseUiState.Error(e.message ?: "Unknown error")
        }
    }

    fun checkForPledge(category: String, onNoPledge: () -> Unit) {
        viewModelScope.launch {
            val pledge = pledgeRepository.getActivePledge(category)
            if (pledge != null) {
                _activePledge.value = pledge
            } else {
                onNoPledge()
            }
        }
    }

    fun dismissPledge() {
        _activePledge.value = null
    }

    fun markPledgeBroken(pledgeId: Long, onComplete: () -> Unit) {
        viewModelScope.launch {
            pledgeRepository.markPledgeBroken(pledgeId)
            _activePledge.value = null
            onComplete()
        }
    }

    private data class PendingExpense(
        val amount: Double,
        val category: String,
        val moodScore: Int?
    )
}
