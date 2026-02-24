package com.impend.shared.analytics

import com.impend.shared.domain.model.Expense

/**
 * ARCH-008: ResilienceEngine
 * Manages the "Cognitive Reframing" content and trigger logic for impulsive spending.
 */
class ResilienceEngine(
    private val riskEngine: ImpulseRiskEngine
) {
    /**
     * Determines if the current logging attempt should be intercepted.
     */
    fun shouldIntervene(history: List<Expense>, current: Expense): Boolean {
        return riskEngine.calculateRiskScore(history, current) > 0.7
    }

    /**
     * Returns a randomized cognitive reframing prompt.
     */
    fun getRandomPrompt(): String {
        return prompts.random()
    }

    private val prompts = listOf(
        "Will this matter in 10 minutes? 10 days? 10 months?",
        "Does this purchase align with your current pledges?",
        "Pause. Take one deep breath. Do you still want this?",
        "Is this a 'Need' or a 'Dopamine Hit'?",
        "Your future self will thank you for pausing. Proceed?"
    )
}
