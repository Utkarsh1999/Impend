package com.impend.shared.analytics

import com.impend.shared.domain.model.Expense
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class DataPoint(val x: Float, val y: Float, val label: String = "")

class AnalyticsTransformer {

    fun transformToDailyTrend(expenses: List<Expense>): List<DataPoint> {
        if (expenses.isEmpty()) return emptyList()

        val grouped = expenses.groupBy { 
            Instant.fromEpochMilliseconds(it.dateMillis)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date.toString()
        }

        return grouped.toList().mapIndexed { index, (date, dailyExpenses) ->
            val total = dailyExpenses.sumOf { it.amount }.toFloat()
            DataPoint(index.toFloat(), total, date)
        }.sortedBy { it.label }
    }

    fun transformToMoodCorrelation(expenses: List<Expense>): List<DataPoint> {
        return expenses.filter { it.moodScore != null }.map {
            DataPoint(
                x = it.moodScore!!.toFloat(),
                y = it.amount.toFloat(),
                label = it.category
            )
        }
    }
    
    fun calculatePledgeMastery(
        totalPledges: Int,
        successfulPledges: Int
    ): Float {
        if (totalPledges == 0) return 1f
        return successfulPledges.toFloat() / totalPledges.toFloat()
    }
}
