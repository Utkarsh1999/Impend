package com.impend.shared.analytics

import com.impend.shared.domain.model.Expense
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ResilienceEngineTest {
    
    private val riskEngine = ImpulseRiskEngine()
    private val resilienceEngine = ResilienceEngine(riskEngine)

    @Test
    fun `test shouldIntervene triggers on high risk`() {
        val history = listOf(
            Expense(1, 10.0, "Coffee", 1000, null, 3),
            Expense(2, 12.0, "Coffee", 2000, null, 3),
            Expense(3, 11.0, "Coffee", 3000, null, 3)
        )
        
        // High amount in a steady category should trigger high risk (> 0.7)
        val highRiskExpense = Expense(4, 100.0, "Coffee", 4000, null, 1) // High amount + low mood
        
        assertTrue(resilienceEngine.shouldIntervene(history, highRiskExpense))
    }

    @Test
    fun `test shouldIntervene does not trigger on low risk`() {
        val history = listOf(
            Expense(1, 10.0, "Coffee", 1000, null, 3),
            Expense(2, 12.0, "Coffee", 2000, null, 3),
            Expense(3, 11.0, "Coffee", 3000, null, 3)
        )
        
        val lowRiskExpense = Expense(4, 11.0, "Coffee", 4000, null, 3)
        
        assertFalse(resilienceEngine.shouldIntervene(history, lowRiskExpense))
    }

    @Test
    fun `test getRandomPrompt returns non-empty string`() {
        val prompt = resilienceEngine.getRandomPrompt()
        assertTrue(prompt.isNotEmpty())
    }
}
