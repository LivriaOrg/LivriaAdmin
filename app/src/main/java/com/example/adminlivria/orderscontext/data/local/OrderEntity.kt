package com.example.adminlivria.orderscontext.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.adminlivria.orderscontext.domain.Item
import com.example.adminlivria.orderscontext.domain.Order
import java.time.Instant

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val id: Int,
    val code: String,
    val userClientId: Int,
    val userEmail: String,
    val userPhone: String,
    val userFullName: String,
    val recipientName: String,
    val isDelivery: Boolean,

    val shippingAddress: String,
    val shippingCity: String,
    val shippingDistrict: String,
    val shippingReference: String,

    val total: Double,
    val date: Instant,
    val status: String
)


fun Order.toEntity(): OrderEntity {
    return OrderEntity(
        id = id,
        code = code,
        userClientId = userClientId,
        userEmail = userEmail,
        userPhone = userPhone,
        userFullName = userFullName,
        recipientName = recipientName,
        isDelivery = isDelivery,

        shippingAddress = shipping.address,
        shippingCity = shipping.city,
        shippingDistrict = shipping.district,
        shippingReference = shipping.reference,

        total = total,
        date = date,
        status = status
    )
}
