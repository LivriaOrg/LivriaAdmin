package com.example.adminlivria.statscontext.domain.model

import androidx.compose.ui.graphics.Color
import com.example.adminlivria.bookcontext.domain.Book

data class BookStatsDomain(
    val bookTitle: String,
    val imageUrl: String,
    val unitsSold: Int
)

data class GenreStatsDomain(
    val genreName: String,
    val inventoryShare: Float,
    val color: Color
)

data class FlowPoint(
    val label: String,
    val value: Float
)

data class CapitalFlowData(
    val inflow: List<FlowPoint>,
    val outflow: List<FlowPoint>
) {
    companion object {
        fun mock(): CapitalFlowData = CapitalFlowData(
            inflow = listOf(
                FlowPoint("Ene", 1000f),
                FlowPoint("Feb", 1200f),
                FlowPoint("Mar", 900f),
                FlowPoint("Abr", 1500f),
            ),
            outflow = listOf(
                FlowPoint("Ene", 500f),
                FlowPoint("Feb", 800f),
                FlowPoint("Mar", 750f),
                FlowPoint("Abr", 1100f),
            )
        )
    }
}
