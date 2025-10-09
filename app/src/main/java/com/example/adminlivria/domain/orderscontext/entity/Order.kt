package com.example.adminlivria.domain.orderscontext.entity

data class Order(
    val id: Int,
    val code: String,
    val userClientId: Int,
    val userEmail: String,
    val userPhone: String,
    val userFullName: String,
    val recipientName: String,
    val status: String,
    val isDelivery: Boolean,
    val shipping: Shipping,
    val total: Double,
    val date: String,
    val items: List<Item>
)
