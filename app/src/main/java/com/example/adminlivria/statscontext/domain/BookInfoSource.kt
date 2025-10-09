package com.example.adminlivria.statscontext.domain

import com.example.adminlivria.bookcontext.domain.Book

interface BookInfoSource {

    suspend fun getTopBooksByStock(limit: Int): List<Book>

    suspend fun getGenreInventoryCount(): Map<String, Int>

    suspend fun getGenreInventoryValue(): Map<String, Float>
}
