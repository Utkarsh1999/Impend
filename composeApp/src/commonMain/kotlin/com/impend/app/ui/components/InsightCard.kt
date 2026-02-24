package com.impend.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.impend.shared.domain.model.Insight
import com.impend.shared.domain.model.InsightType
import com.impend.app.ui.theme.ImpendColors

@Composable
fun InsightCarousel(
    insights: List<Insight>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            items(insights) { insight ->
                InsightCard(
                    insight = insight,
                    modifier = Modifier.width(280.dp)
                )
            }
        }
    }
}

@Composable
fun InsightCard(
    insight: Insight,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (insight.type) {
        InsightType.RISK_WARNING -> Color(0xFFFFEBEE) // Light Red
        InsightType.MOOD_CORRELATION -> Color(0xFFFFF3E0) // Light Orange
        InsightType.POSITIVE_STREAK -> Color(0xFFE8F5E9) // Light Green
        InsightType.SPENDING_PATTERN -> Color(0xFFE3F2FD) // Light Blue
    }
    
    val icon = when (insight.type) {
        InsightType.RISK_WARNING -> "⚠️"
        InsightType.MOOD_CORRELATION -> "🧠"
        InsightType.POSITIVE_STREAK -> "🌟"
        InsightType.SPENDING_PATTERN -> "📊"
    }

    AppCard(
        modifier = modifier.height(110.dp),
        // Use a slight tint for the card background to make it stand out
    ) {
        Box(modifier = Modifier.fillMaxSize().background(backgroundColor.copy(alpha = 0.3f))) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = icon, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = insight.title,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = ImpendColors.TextPrimary
                    )
                }
                
                Text(
                    text = insight.recommendation,
                    style = MaterialTheme.typography.caption,
                    color = ImpendColors.TextSecondary,
                    maxLines = 3
                )
            }
        }
    }
}
