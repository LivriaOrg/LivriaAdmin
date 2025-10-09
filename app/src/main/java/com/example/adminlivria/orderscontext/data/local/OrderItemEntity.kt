package com.example.adminlivria.orderscontext.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookId: Int,
    val bookTitle: String,
    val bookAuthor: String,
    val bookPrice: Double,
    val bookCover: String,
    val quantity: Int,
    val itemTotal: Double,
    val orderId: Int
)