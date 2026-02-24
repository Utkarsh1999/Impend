package com.impend.shared.domain.repository

import com.impend.shared.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun addExpense(expense: Expense)
    suspend fun deleteExpense(id: Long)
    suspend fun getExpenseById(id: Long): Expense?
    fun getAllExpenses(): Flow<List<Expense>>
    suspend fun clearAllExpenses()
    fun getExpensesInRange(startMillis: Long, endMillis: Long): Flow<List<Expense>>
}
