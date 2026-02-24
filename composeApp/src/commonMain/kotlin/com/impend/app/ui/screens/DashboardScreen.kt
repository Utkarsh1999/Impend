package com.impend.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.impend.app.presentation.HomeUiState
import com.impend.app.ui.components.AppCard
import com.impend.app.ui.components.InsightCarousel
import com.impend.app.ui.components.MomentumCard
import com.impend.app.ui.theme.ImpendColors
import com.impend.shared.util.CurrencyFormatter
import org.jetbrains.compose.resources.stringResource
import impend.composeapp.generated.resources.Res
import impend.composeapp.generated.resources.*

@Composable
fun DashboardScreen(
    state: HomeUiState.Success,
    onAddClick: () -> Unit,
    onPledgeClick: () -> Unit,
    onProgressClick: () -> Unit,
    onCirclesClick: () -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section
        item {
            Column {
                Text(
                    text = stringResource(Res.string.dashboard_overview_title),
                    style = MaterialTheme.typography.h2,
                    color = ImpendColors.TextPrimary
                )
                Text(
                    text = stringResource(Res.string.dashboard_overview_subtitle),
                    style = MaterialTheme.typography.caption,
                    color = ImpendColors.TextSecondary
                )
            }
        }

        // Impulse Risk Dial
        item {
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(stringResource(Res.string.dashboard_risk_score), style = MaterialTheme.typography.body2)
                        val riskPercent = (state.avgImpulseRisk * 100).toInt()
                        val riskColor = when {
                            state.avgImpulseRisk > 0.7 -> ImpendColors.Error
                            state.avgImpulseRisk > 0.4 -> ImpendColors.Warning
                            else -> ImpendColors.Success
                        }
                        Text(
                            text = "$riskPercent%",
                            style = MaterialTheme.typography.h1.copy(fontSize = 32.sp),
                            color = riskColor
                        )
                    }
                    Text(
                        text = if (state.avgImpulseRisk > 0.6) stringResource(Res.string.dashboard_risk_high) else stringResource(Res.string.dashboard_risk_stable),
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        // Metrics Row
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AppCard(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(stringResource(Res.string.dashboard_spent), style = MaterialTheme.typography.caption)
                        Text(CurrencyFormatter.format(state.totalSpending, state.currencyCode), style = MaterialTheme.typography.h2.copy(fontSize = 20.sp))
                    }
                }
                AppCard(modifier = Modifier.weight(1f)) {
                    Column {
                        Text(stringResource(Res.string.dashboard_mood_pulse), style = MaterialTheme.typography.caption)
                        val moodEmoji = getEmoji(state.avgMoodScore.toInt())
                        val moodText = if (state.avgMoodScore >= 4) stringResource(Res.string.dashboard_mood_positive) else if (state.avgMoodScore <= 2) stringResource(Res.string.dashboard_mood_stress) else stringResource(Res.string.dashboard_mood_neutral)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(moodEmoji, fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(moodText, style = MaterialTheme.typography.h2.copy(fontSize = 18.sp))
                        }
                    }
                }
            }
        }

        // Coaching Insights Carousel
        if (state.insights.isNotEmpty()) {
            item {
                Text(stringResource(Res.string.dashboard_coaching_insights), style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                InsightCarousel(insights = state.insights)
            }
        }

        // Social Momentum Widget
        if (state.joinedCircles.isNotEmpty()) {
            item {
                MomentumCard(joinedCircles = state.joinedCircles)
            }
        }

        // Recent Activity
        item {
            Text(stringResource(Res.string.dashboard_recent_activity), style = MaterialTheme.typography.h2.copy(fontSize = 22.sp))
        }

        items(state.expenses, key = { it.id.toString() + it.dateMillis.toString() }) { expense ->
            RecentExpenseItem(
                expense = expense,
                currencyCode = state.currencyCode,
                onDelete = { onDeleteClick(expense.id) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecentExpenseItem(
    expense: com.impend.shared.domain.model.Expense,
    currencyCode: String,
    onDelete: () -> Unit
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                onDelete()
                true
            } else false
        }
    )

    SwipeToDismiss(
        state = dismissState,
        background = {
            Box(
                Modifier.fillMaxSize().padding(horizontal = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                 // Background content for swipe
            }
        },
        dismissContent = {
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(expense.category, style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold)
                        Text("Emotion: ${getEmoji(expense.moodScore)}", style = MaterialTheme.typography.caption)
                    }
                    Text(CurrencyFormatter.format(expense.amount, currencyCode), style = MaterialTheme.typography.h2.copy(fontSize = 18.sp), color = ImpendColors.Secondary)
                }
            }
        }
    )
}

private fun getEmoji(score: Int?): String = when(score) {
    1 -> "😢"
    2 -> "😕"
    3 -> "😐"
    4 -> "🙂"
    5 -> "🤩"
    else -> "None"
}
