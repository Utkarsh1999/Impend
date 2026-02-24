package com.impend.shared.analytics

import com.impend.shared.domain.model.Expense

class ImpulseRiskEngine {

    /**
     * Calculates the Impulse Risk Score™ based on Z-Score anomaly 
     * detection matching expense amounts to the user's historical rolling average 
     * in specific categories, combined with mood score correlation.
     *
     * Returns a score from 0.0 (No Risk) to 1.0 (High Impulse).
     */
    fun calculateRiskScore(expenses: List<Expense>, currentExpense: Expense): Double {
        if (expenses.isEmpty()) return 0.0

        val categoryExpenses = expenses.filter { it.category == currentExpense.category }
        if (categoryExpenses.isEmpty()) return 0.5 // Unknown category baseline

        val mean = categoryExpenses.map { it.amount }.average()
        val variance = categoryExpenses.map { (it.amount - mean) * (it.amount - mean) }.average()
        val stdDev = kotlin.math.sqrt(variance)

        if (stdDev == 0.0) {
            return if (currentExpense.amount > mean * 1.5) 0.8 else 0.2
        }

        val zScore = (currentExpense.amount - mean) / stdDev
        
        // Base risk from zScore. Z > 2 implies top 2.5% tail
        var risk = when {
            zScore > 3.0 -> 0.95
            zScore > 2.0 -> 0.80
            zScore > 1.0 -> 0.60
            zScore < 0.0 -> 0.10
            else -> 0.30
        }

        // Mood correlation penalty (e.g., spending huge amount while mood score is 1 (Sad))
        if (currentExpense.moodScore != null && currentExpense.moodScore <= 2) {
            risk = minOf(1.0, risk + 0.15) // Boost risk if mood is low indicating emotional spending
        }

        return risk
    }
}
