package com.impend.shared.analytics

import com.impend.shared.domain.model.Expense
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BehavioralAnalyticsEngineTest {

    private val engine = BehavioralAnalyticsEngine()

    @Test
    fun testStressSpendingCorrelation() {
        // High amounts associated with low mood (1, 2)
        // Low amounts associated with high mood (4, 5)
        // This should produce a negative correlation
        val expenses = listOf(
            Expense(1L, 200.0, "Misc", 1000L, moodScore = 1),
            Expense(2L, 180.0, "Misc", 2000L, moodScore = 2),
            Expense(3L, 20.0, "Misc", 3000L, moodScore = 5),
            Expense(4L, 15.0, "Misc", 4000L, moodScore = 4)
        )

        val correlation = engine.calculateMoodSpendingCorrelation(expenses)
        assertTrue(correlation < -0.8, "Correlation should be strongly negative ($correlation)")
    }

    @Test
    fun testRewardSpendingCorrelation() {
        // High amounts associated with high mood
        val expenses = listOf(
            Expense(1L, 200.0, "Misc", 1000L, moodScore = 5),
            Expense(2L, 180.0, "Misc", 2000L, moodScore = 4),
            Expense(3L, 20.0, "Misc", 3000L, moodScore = 1),
            Expense(4L, 15.0, "Misc", 4000L, moodScore = 2)
        )

        val correlation = engine.calculateMoodSpendingCorrelation(expenses)
        assertTrue(correlation > 0.8, "Correlation should be strongly positive ($correlation)")
    }
}
