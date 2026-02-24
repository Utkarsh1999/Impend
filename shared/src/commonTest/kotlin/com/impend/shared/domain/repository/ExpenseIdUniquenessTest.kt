package com.impend.shared.domain.repository

import com.impend.shared.domain.model.Expense
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExpenseIdUniquenessTest {

    @Test
    fun testExpenseIdsAreUnique() {
        val expenses = listOf(
            Expense(id = 1L, amount = 10.0, category = "A", dateMillis = 1000L),
            Expense(id = 2L, amount = 20.0, category = "B", dateMillis = 2000L),
            Expense(id = 3L, amount = 30.0, category = "C", dateMillis = 3000L)
        )
        
        val uniqueIds = expenses.map { it.id }.toSet()
        assertEquals(expenses.size, uniqueIds.size, "IDs in the expense list must be unique to prevent UI crashes.")
    }
}
