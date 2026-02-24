package com.impend.shared.analytics

import com.impend.shared.domain.model.Expense
import kotlin.test.Test
import kotlin.test.assertTrue

class ImpulseRiskEngineTest {

    private val engine = ImpulseRiskEngine()

    @Test
    fun testHighRiskAnomaly() {
        val history = listOf(
            Expense(1L, 10.0, "Coffee", 1000L),
            Expense(2L, 12.0, "Coffee", 2000L),
            Expense(3L, 11.0, "Coffee", 3000L)
        )
        // Way above average, high emotion (low mood score)
        val outlier = Expense(4L, 150.0, "Coffee", 4000L, moodScore = 1) 
        
        val score = engine.calculateRiskScore(history, outlier)
        assertTrue(score > 0.8, "Score should be high for $150 coffee when mood is low")
    }

    @Test
    fun testLowRiskNormalExpense() {
        val history = listOf(
            Expense(1L, 50.0, "Groceries", 1000L),
            Expense(2L, 55.0, "Groceries", 2000L)
        )
        // Normal expense, positive mood
        val normal = Expense(3L, 52.0, "Groceries", 3000L, moodScore = 4)
        
        val score = engine.calculateRiskScore(history, normal)
        assertTrue(score < 0.4, "Score should be low for normal groceries")
    }
}
