package com.example.adminlivria.statscontext.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp

@Composable
fun BarChartCanvas(
    genres: List<GenreStatsUi>,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    if (genres.isEmpty()) return

    val maxMonetaryValue = genres.maxOfOrNull { it.inventoryShare } ?: 1f

    Canvas(modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        val totalWidth = size.width
        val totalHeight = size.height
        val barCount = genres.size

        val spacing = totalWidth / (barCount * 4f)
        val barWidth = (totalWidth - (spacing * (barCount + 1))) / barCount

        val baselineY = totalHeight
        drawLine(
            color = androidx.compose.ui.graphics.Color.LightGray,
            start = Offset(0f, baselineY),
            end = Offset(totalWidth, baselineY),
            strokeWidth = 2f
        )

        genres.forEachIndexed { index, genre ->
            val scaleFactor = (genre.inventoryShare / maxMonetaryValue)
            val barHeight = scaleFactor * totalHeight * 0.9f

            val xStart = spacing + (index * (barWidth + spacing))

            val left = xStart
            val top = baselineY - barHeight
            val right = xStart + barWidth

            drawRect(
                color = genre.color,
                topLeft = Offset(left, top),
                size = Size(barWidth, barHeight)
            )
        }
    }
}
