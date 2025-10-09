package com.example.adminlivria.statscontext.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.adminlivria.statscontext.domain.model.CapitalFlowData
import com.example.adminlivria.statscontext.domain.model.FlowPoint

val InflowColor = Color(0xFF4CAF50)
val OutflowColor = Color(0xFFF44336)

@Composable
fun LineChartCanvas(data: CapitalFlowData, modifier: Modifier = Modifier) {

    if (data.inflow.isEmpty() || data.outflow.isEmpty()) return

    Canvas(modifier = modifier.fillMaxSize().padding(16.dp)) {

        val padding = 20.dp.toPx()
        val contentWidth = size.width - 2 * padding
        val contentHeight = size.height - 2 * padding

        val allValues = data.inflow.map { it.value } + data.outflow.map { it.value }
        val maxValue = allValues.maxOrNull() ?: 1f
        val minValue = allValues.minOrNull() ?: 0f

        val valueRange = (maxValue - minValue) * 1.1f

        val pointCount = data.inflow.size.coerceAtLeast(data.outflow.size)
        val xStep = if (pointCount > 1) contentWidth / (pointCount - 1) else 0f

        fun mapValueToY(value: Float): Float {
            val normalizedValue = (value - minValue) / valueRange
            return contentHeight * (1f - normalizedValue) + padding
        }

        val inflowPath = Path()
        val outflowPath = Path()

        for (i in 0 until pointCount) {
            val x = padding + i * xStep

            val inflowValue = data.inflow.getOrNull(i)?.value ?: 0f
            val inflowY = mapValueToY(inflowValue)

            val outflowValue = data.outflow.getOrNull(i)?.value ?: 0f
            val outflowY = mapValueToY(outflowValue)

            if (i == 0) {
                inflowPath.moveTo(x, inflowY)
                outflowPath.moveTo(x, outflowY)
            } else {
                inflowPath.lineTo(x, inflowY)
                outflowPath.lineTo(x, outflowY)
            }

            drawCircle(InflowColor, radius = 5.dp.toPx(), center = Offset(x, inflowY))
            drawCircle(OutflowColor, radius = 5.dp.toPx(), center = Offset(x, outflowY))
        }

        drawPath(
            path = inflowPath,
            color = InflowColor,
            style = Stroke(width = 3.dp.toPx())
        )
        drawPath(
            path = outflowPath,
            color = OutflowColor,
            style = Stroke(width = 3.dp.toPx())
        )
    }
}
