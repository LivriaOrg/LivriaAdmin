package com.example.adminlivria.bookcontext.domain

data class BookFilters(
    val genre: String? = null,
    val language: String? = null,
    val sort: SortOption = SortOption.NONE
)

enum class SortOption { NONE, TITLE_ASC, TITLE_DESC }