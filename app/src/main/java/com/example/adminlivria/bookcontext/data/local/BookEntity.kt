package com.example.adminlivria.bookcontext.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.adminlivria.bookcontext.domain.Book

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val author: String,
    val genre: String,
    val language: String,
    val price: Double,
    val stock: Int,
    val cover: String
)

fun BookEntity.toDomain() = Book(
    id, title, description, author, genre, language, price, stock, cover
)

fun Book.toEntity() = BookEntity(
    id, title, description, author, genre, language, price, stock, cover
)