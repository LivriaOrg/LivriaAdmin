package com.example.adminlivria.bookcontext.domain

data class Book(
    val id: Int = 0,
    val title: String,
    val description: String,
    val author: String,
    val genre: String,
    val language: String,
    val price: Double,
    val stock: Int,
    val cover: String
)