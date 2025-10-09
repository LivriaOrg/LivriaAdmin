package com.example.adminlivria.domain.orderscontext.entity

data class Item(
    val id: Int,
    val bookId: Int,
    val bookTitle: String,
    val bookAuthor: String,
    val bookPrice: Double,
    val bookCover: String,
    val quantity: Int,
    val itemTotal: Double
)
