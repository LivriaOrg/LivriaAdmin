package com.example.adminlivria.orderscontext.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(orderEntity: OrderEntity): Long

    @Insert
    suspend fun insertItem(orderItemEntity: OrderItemEntity)

    @Update
    suspend fun update(orderEntity: OrderEntity)

    // Consultas que obtienen la Order completa (Order + Items)
    @Transaction
    @Query("select * from orders")
    suspend fun fetchAllOrdersWithItems(): List<OrderWithItems>

    @Transaction
    @Query("select * from orders where id =:id")
    suspend fun fetchOrderWithItemsById(id: Int): OrderWithItems?

}