package com.example.adminlivria.orderscontext.presentation.orderdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.adminlivria.common.Resource
import com.example.adminlivria.common.UIState
import com.example.adminlivria.common.orderServiceInstance
import com.example.adminlivria.orderscontext.data.local.AppDatabase
import com.example.adminlivria.orderscontext.data.repository.OrderRepository
import com.example.adminlivria.orderscontext.domain.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderDetailViewModel(
    private val repository: OrderRepository,
    private val orderId: Int
) : ViewModel() {

    private val _state = MutableStateFlow(UIState<Order>())
    val state: StateFlow<UIState<Order>> = _state

    init {
        loadOrderDetail()
    }

    private fun loadOrderDetail() {
        viewModelScope.launch {
            _state.value = UIState(isLoading = true)

            val result = repository.getOrderById(orderId)
            _state.value = when (result) {
                is Resource.Success -> UIState(data = result.data)
                is Resource.Error -> UIState(message = result.message ?: "Error loading order details")
            }
        }
    }
}

class OrderDetailViewModelFactory(private val context: Context, private val orderId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AppDatabase.getInstance(context)
        val repo = OrderRepository(orderServiceInstance, dao.getOrderDao())
        return OrderDetailViewModel(repo, orderId) as T
    }
}