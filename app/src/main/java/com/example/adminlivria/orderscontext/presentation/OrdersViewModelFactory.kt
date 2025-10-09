package com.example.adminlivria.orderscontext.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adminlivria.common.orderServiceInstance
import com.example.adminlivria.orderscontext.data.local.AppDatabase
import com.example.adminlivria.orderscontext.data.repository.OrderRepository

class OrdersViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AppDatabase.getInstance(context).getOrderDao()
        val repository = OrderRepository(orderServiceInstance, dao)
        return OrdersViewModel(repository) as T
    }
}