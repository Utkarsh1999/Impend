package com.impend.shared.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.impend.shared.data.local.ImpendDatabase
import com.impend.shared.domain.model.Expense
import com.impend.shared.domain.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(
    db: ImpendDatabase
) : ExpenseRepository {

    private val queries = db.expenseEntityQueries

    override suspend fun addExpense(expense: Expense) {
        queries.insertExpense(
            amount = expense.amount,
            category = expense.category,
            dateMillis = expense.dateMillis,
            customTag = expense.customTag,
            moodScore = expense.moodScore?.toLong()
        )
    }

    override suspend fun deleteExpense(id: Long) {
        queries.deleteExpenseById(id)
    }

    override suspend fun getExpenseById(id: Long): Expense? {
        val entity = queries.getExpenseById(id).executeAsOneOrNull()
        return entity?.let {
            Expense(
                id = it.id,
                amount = it.amount,
                category = it.category,
                dateMillis = it.dateMillis,
                customTag = it.customTag,
                moodScore = it.moodScore?.toInt()
            )
        }
    }

    override fun getAllExpenses(): Flow<List<Expense>> {
        return queries.getAllExpenses()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { list ->
                list.map { entity ->
                    Expense(
                        id = entity.id,
                        amount = entity.amount,
                        category = entity.category,
                        dateMillis = entity.dateMillis,
                        customTag = entity.customTag,
                        moodScore = entity.moodScore?.toInt()
                    )
                }
            }
    }

    override suspend fun clearAllExpenses() {
        queries.clearAllExpenses()
    }

    override fun getExpensesInRange(startMillis: Long, endMillis: Long): Flow<List<Expense>> {
        return queries.getExpensesInRange(startMillis, endMillis)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { list ->
                list.map { entity ->
                    Expense(
                        id = entity.id,
                        amount = entity.amount,
                        category = entity.category,
                        dateMillis = entity.dateMillis,
                        customTag = entity.customTag,
                        moodScore = entity.moodScore?.toInt()
                    )
                }
            }
    }
}
