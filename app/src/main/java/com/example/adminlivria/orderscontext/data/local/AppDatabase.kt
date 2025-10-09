package com.example.adminlivria.orderscontext.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.adminlivria.common.DateConverter

@Database(
    entities = [OrderEntity::class, OrderItemEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getOrderDao(): OrderDao

}