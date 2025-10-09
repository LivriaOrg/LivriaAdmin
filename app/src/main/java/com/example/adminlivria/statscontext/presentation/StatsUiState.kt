package com.example.adminlivria.statscontext.presentation

import androidx.compose.ui.graphics.Color
data class BookStatsUi(
    val title: String,
    val imageUrl: String,
    val units: Int
)

data class GenreStatsUi(
    val genre: String,
    val inventoryShare: Float,
    val color: Color,
    val percentageText: String = "%.1f%%".format(inventoryShare * 100)
)

data class StatsUiState(
    val topSellingBooks: List<BookStatsUi> = emptyList(),
    val inventoryValueByGenre: List<GenreStatsUi> = emptyList(),
    val revenueByGenre: List<GenreStatsUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)