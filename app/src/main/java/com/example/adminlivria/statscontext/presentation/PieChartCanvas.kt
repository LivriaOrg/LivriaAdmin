package com.example.adminlivria.statscontext.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PieChartCanvas(genres: List<GenreStatsUi>, modifier: Modifier = Modifier) {
    val totalValue = genres.sumOf { it.inventoryShare.toDouble() }.toFloat()

    if (totalValue <= 0f) return

    Canvas(
        modifier = modifier
            .fillMaxWidth(0.6f)
            .aspectRatio(1f)
    ) {
        val diameter = size.minDimension
        val radius = diameter / 2f
        val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
        val arcSize = Size(diameter, diameter)

        var currentStartAngle = -90f

        genres.forEach { genre ->
            val normalizedShare = genre.inventoryShare / totalValue
            val sweepAngle = normalizedShare * 360f

            drawArc(
                color = genre.color,
                startAngle = currentStartAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = topLeft,
                size = arcSize
            )

            currentStartAngle += sweepAngle
        }
    }
}
