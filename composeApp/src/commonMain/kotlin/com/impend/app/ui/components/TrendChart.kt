package com.impend.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.impend.shared.analytics.DataPoint
import com.impend.app.ui.theme.ImpendColors

@Composable
fun TrendChart(
    dataPoints: List<DataPoint>,
    modifier: Modifier = Modifier,
    lineColor: Color = ImpendColors.Primary
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        if (dataPoints.isEmpty()) return@Canvas

        // Handle single-point case
        if (dataPoints.size == 1) {
            val point = dataPoints.first()
            val y = size.height / 2f
            drawCircle(
                color = lineColor,
                radius = 6.dp.toPx(),
                center = Offset(size.width / 2f, y)
            )
            return@Canvas
        }

        val spacing = size.width / (dataPoints.size - 1)
        val maxY = dataPoints.maxOf { it.y }.coerceAtLeast(1f)
        val minY = dataPoints.minOf { it.y }
        val rangeY = (maxY - minY).coerceAtLeast(1f)
        val factorY = size.height / rangeY

        val path = Path().apply {
            dataPoints.forEachIndexed { index, point ->
                val x = index * spacing
                val y = size.height - ((point.y - minY) * factorY)
                if (index == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3.dp.toPx())
        )

        // Fill area under the curve
        val fillPath = Path().apply {
            addPath(path)
            lineTo((dataPoints.size - 1) * spacing, size.height)
            lineTo(0f, size.height)
            close()
        }
        
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(lineColor.copy(alpha = 0.3f), Color.Transparent)
            )
        )
        
        // Draw points
        dataPoints.forEachIndexed { index, point ->
            drawCircle(
                color = lineColor,
                radius = 4.dp.toPx(),
                center = Offset(index * spacing, size.height - ((point.y - minY) * factorY))
            )
        }
    }
}
