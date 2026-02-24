package com.impend.shared.domain.model

data class Insight(
    val title: String,
    val recommendation: String,
    val type: InsightType,
    val importance: Float // 0.0 to 1.0
)

enum class InsightType {
    RISK_WARNING,
    MOOD_CORRELATION,
    SPENDING_PATTERN,
    POSITIVE_STREAK
}
