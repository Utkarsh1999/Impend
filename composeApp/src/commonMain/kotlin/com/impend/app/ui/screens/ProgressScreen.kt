package com.impend.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.impend.app.presentation.ProgressViewModel
import com.impend.app.ui.components.AppCard
import com.impend.app.ui.components.TrendChart
import com.impend.app.ui.theme.ImpendColors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProgressScreen(
    onBack: () -> Unit,
    viewModel: ProgressViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Behavioral Growth", style = MaterialTheme.typography.h1)
            TextButton(onClick = onBack) {
                Text("Back", color = ImpendColors.Primary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = ImpendColors.Primary)
            }
        } else {
            // Spending Trend Card
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Daily Spending Trend", style = MaterialTheme.typography.h2)
                    Spacer(modifier = Modifier.height(16.dp))
                    if (state.dailyTrend.isNotEmpty()) {
                        TrendChart(dataPoints = state.dailyTrend)
                    } else {
                        Text("Need more data to show trends", style = MaterialTheme.typography.caption)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pledge Mastery Card
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Pledge Mastery", style = MaterialTheme.typography.h2)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val masteryPercent = (state.pledgeMastery * 100).toInt()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "$masteryPercent%",
                                style = MaterialTheme.typography.h1.copy(fontSize = 36.sp),
                                color = if (state.pledgeMastery > 0.8f) ImpendColors.Success else ImpendColors.Primary
                            )
                            Text("Behavioral Commitment Success Rate", style = MaterialTheme.typography.caption)
                        }
                        
                        Text(
                            text = if (state.pledgeMastery > 0.9f) "👑" else if (state.pledgeMastery > 0.7f) "🌟" else "🌱",
                            fontSize = 48.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mood vs Spend Insight Card
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Mood & Spending Correlation", style = MaterialTheme.typography.h2)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "We noticed your spending tends to increase when your mood is lower. High-discipline days correlate with higher mood scores.",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
