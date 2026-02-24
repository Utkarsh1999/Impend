package com.impend.shared.analytics

import com.impend.shared.domain.model.Insight
import com.impend.shared.domain.model.InsightType

/**
 * ARCH-004: InsightGenerator
 * A pure Kotlin component that converts raw analytics metrics into human-readable coaching tips.
 */
class InsightGenerator {

    fun generateInsights(
        avgImpulseRisk: Double,
        moodCorrelation: Double,
        avgMoodScore: Double
    ): List<Insight> {
        val insights = mutableListOf<Insight>()

        // Rule 1: High Impulse Risk Warning
        if (avgImpulseRisk > 0.6) {
            insights.add(
                Insight(
                    title = "High Impulse Alert",
                    recommendation = "Recent spending shows a high impulse pattern. Try the 24-hour rule before next big purchase.",
                    type = InsightType.RISK_WARNING,
                    importance = avgImpulseRisk.toFloat()
                )
            )
        }

        // Rule 2: Stress Spending Detection
        if (moodCorrelation < -0.4 && avgMoodScore < 3.0) {
            insights.add(
                Insight(
                    title = "Stress Spending Pattern",
                    recommendation = "You tend to spend more when feeling stressed. Maybe a quick walk could replace the next impulse buy?",
                    type = InsightType.MOOD_CORRELATION,
                    importance = 0.9f
                )
            )
        }

        // Rule 3: Reward Spending Detection
        if (moodCorrelation > 0.4 && avgMoodScore > 4.0) {
            insights.add(
                Insight(
                    title = "Celebratory Spending",
                    recommendation = "You're in a great mood! Celebrate your wins with a low-cost reward to keep the momentum going.",
                    type = InsightType.POSITIVE_STREAK,
                    importance = 0.7f
                )
            )
        }

        // Rule 4: Neutral Baseline
        if (insights.isEmpty()) {
            insights.add(
                Insight(
                    title = "Steady Progress",
                    recommendation = "Your spending is balanced and logic-driven today. Keep up the disciplined work!",
                    type = InsightType.SPENDING_PATTERN,
                    importance = 0.5f
                )
            )
        }

        return insights.sortedByDescending { it.importance }
    }
}
