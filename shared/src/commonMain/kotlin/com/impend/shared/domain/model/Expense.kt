package com.impend.shared.domain.model

data class Expense(
    val id: Long,
    val amount: Double,
    val category: String,
    val dateMillis: Long,
    val customTag: String? = null,
    val moodScore: Int? = null // 1 to 5 scale
)
