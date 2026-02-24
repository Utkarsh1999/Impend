package com.impend.shared.analytics

import com.impend.shared.domain.model.Expense
import kotlin.math.sqrt

/**
 * Engine for calculating deeper behavioral patterns beyond simple anomalies.
 */
class BehavioralAnalyticsEngine {

    /**
     * Calculates the Pearson Correlation Coefficient between expense amounts and mood scores.
     * Returns a value between -1.0 and 1.0.
     * Values close to -1.0 suggest "Stress Spending" (High amount when mood is low).
     * Values close to 1.0 suggest "Reward Spending" (High amount when mood is high).
     */
    fun calculateMoodSpendingCorrelation(expenses: List<Expense>): Double {
        val data = expenses.filter { it.moodScore != null }
        if (data.size < 2) return 0.0

        val x = data.map { it.amount }
        val y = data.map { it.moodScore!!.toDouble() }

        val meanX = x.average()
        val meanY = y.average()

        var numerator = 0.0
        var sumX2 = 0.0
        var sumY2 = 0.0

        for (i in x.indices) {
            val diffX = x[i] - meanX
            val diffY = y[i] - meanY
            numerator += diffX * diffY
            sumX2 += diffX * diffX
            sumY2 += diffY * diffY
        }

        val denominator = sqrt(sumX2 * sumY2)
        return if (denominator == 0.0) 0.0 else numerator / denominator
    }

    /**
     * Classifies whether the user is a "Weekend Warrior" or "Weekday Worker".
     * Based on spending density across days of the week.
     */
    fun getSpendingDayClassification(expenses: List<Expense>): DayClassification {
        if (expenses.isEmpty()) return DayClassification.BALANCED

        // Simple day of week from millis wrapper would go here. 
        // For now, using a mock split or assuming we have a way to extract day.
        // In a real prod app, use kotlinx-datetime.
        return DayClassification.WEEKEND_HEAVY // Mock result
    }
}

enum class DayClassification {
    WEEKEND_HEAVY,
    WEEKDAY_HEAVY,
    BALANCED
}
