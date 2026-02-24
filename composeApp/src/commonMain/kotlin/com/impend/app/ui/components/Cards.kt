package com.impend.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.impend.app.ui.theme.ImpendColors

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(ImpendColors.Surface)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(ImpendColors.GlassBorder, ImpendColors.GlassBorder.copy(alpha = 0.05f))
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(ImpendColors.GlassOverlay)
            .border(1.dp, ImpendColors.GlassBorder, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        content()
    }
}
