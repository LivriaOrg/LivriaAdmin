package com.example.adminlivria.orderscontext.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update


@Dao
interface OrderDao {

    @Update
    suspend fun update(orderEntity: OrderEntity)

    @Query("select * from orders")
    suspend fun fetchAll(): List<OrderEntity>

    @Query("select * from orders where id = :id")
    suspend fun fetchById(id: Int): OrderEntity?

}
