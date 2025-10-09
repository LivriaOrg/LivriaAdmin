package com.example.adminlivria.statscontext.data.repository

import com.example.adminlivria.statscontext.data.local.StatsDao
import com.example.adminlivria.statscontext.domain.BookInfoSource
import com.example.adminlivria.statscontext.domain.model.BookStatsDomain
import com.example.adminlivria.statscontext.domain.model.GenreStatsDomain
import androidx.compose.ui.graphics.Color

class StatsRepository(
    private val dao: StatsDao,
    private val bookInfoSource: BookInfoSource
) {

    private fun mapGenreToColor(genreName: String): Color {
        return when (genreName.lowercase()) {
            "literature" -> Color(0xFFF06292)
            "mangas_comics" -> Color(0xFF64B5F6)
            "juvenile" -> Color(0xFFFFCC80)
            "thriller" -> Color(0xFF81C784)
            "science_fiction" -> Color(0xFFBA68C8)
            else -> Color.Gray
        }
    }
    suspend fun fetchTopSellingBooks(): List<BookStatsDomain> {
        val topBooks = bookInfoSource.getTopBooksByStock(limit = 3)
        return topBooks.map { book ->
            BookStatsDomain(book.title, book.cover, book.stock)
        }
    }
    suspend fun fetchGenreDistributionByStock(): List<GenreStatsDomain> {
        val genreValueMap = bookInfoSource.getGenreInventoryCount()
        val totalStockCount = genreValueMap.values.sum().toFloat()

        if (totalStockCount == 0f) return emptyList()

        return genreValueMap.map { (genreName, genreCount) ->
            val share = genreCount.toFloat() / totalStockCount

            GenreStatsDomain(
                genreName = genreName,
                inventoryShare = share,
                color = mapGenreToColor(genreName)
            )
        }.sortedByDescending { it.inventoryShare }
    }

    suspend fun fetchInventoryValueByGenre(): List<GenreStatsDomain> {

        val genreStockCountMap = bookInfoSource.getGenreInventoryValue()

        return genreStockCountMap.map { (genreName, genreMonetaryValue) ->
            GenreStatsDomain(
                genreName = genreName,
                inventoryShare = genreMonetaryValue,
                color = mapGenreToColor(genreName)
            )
        }.sortedByDescending { it.inventoryShare }
    }
}
