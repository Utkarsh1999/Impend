package com.impend.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.impend.app.ui.theme.ImpendColors

/**
 * ARCH-002: MoodSelector
 * A 5-point emotional scale selector with visual feedback.
 */
@Composable
fun MoodSelector(
    selectedMood: Int?,
    onMoodSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..5).forEach { mood ->
                val isSelected = mood == selectedMood
                val moodColor = getMoodColor(mood)
                
                MoodItem(
                    mood = mood,
                    isSelected = isSelected,
                    highlightColor = moodColor,
                    onClick = { onMoodSelected(mood) }
                )
            }
        }
        
        selectedMood?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Feeling: ${getMoodLabel(it)}",
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight.Bold,
                    color = getMoodColor(it)
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun MoodItem(
    mood: Int,
    isSelected: Boolean,
    highlightColor: Color,
    onClick: () -> Unit
) {
    val emoji = getEmoji(mood)

    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(if (isSelected) highlightColor.copy(alpha = 0.2f) else Color.Transparent)
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji, 
            fontSize = if (isSelected) 32.sp else 24.sp,
            modifier = Modifier.padding(4.dp)
        )
    }
}

private fun getEmoji(mood: Int): String = when (mood) {
    1 -> "😢"
    2 -> "😕"
    3 -> "😐"
    4 -> "🙂"
    5 -> "🤩"
    else -> "😐"
}

private fun getMoodLabel(mood: Int): String = when (mood) {
    1 -> "Very Stressed 😢"
    2 -> "Slightly Anxious 😕"
    3 -> "Neutral 😐"
    4 -> "Good 🙂"
    5 -> "Great! 🤩"
    else -> "Neutral"
}

private fun getMoodColor(mood: Int): Color = when (mood) {
    1 -> Color(0xFFE57373) // Red
    2 -> Color(0xFFFFB74D) // Orange
    3 -> Color(0xFFFFD54F) // Yellow
    4 -> Color(0xFF81C784) // Green
    5 -> Color(0xFF4FC3F7) // Blue/Vibrant
    else -> ImpendColors.Primary
}
