package com.impend.shared.domain.usecase

import com.impend.shared.domain.model.Expense
import com.impend.shared.domain.repository.ExpenseRepository

class AddExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(
        amount: Double,
        category: String,
        dateMillis: Long,
        customTag: String? = null,
        moodScore: Int? = null
    ) {
        require(amount > 0) { "Amount must be greater than 0" }
        if (moodScore != null) {
            require(moodScore in 1..5) { "Mood score must be between 1 and 5" }
        }

        val expense = Expense(
            id = 0L,
            amount = amount,
            category = category,
            dateMillis = dateMillis,
            customTag = customTag,
            moodScore = moodScore
        )
        expenseRepository.addExpense(expense)
    }
}
