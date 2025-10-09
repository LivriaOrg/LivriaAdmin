package com.example.adminlivria.orderscontext.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adminlivria.common.Resource
import com.example.adminlivria.common.UIState
import com.example.adminlivria.orderscontext.data.repository.OrderRepository
import com.example.adminlivria.orderscontext.domain.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class OrdersViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    private val _search = mutableStateOf("")
    val search: State<String> get() = _search

    private val _state = mutableStateOf(UIState<List<Order>>())
    val state: State<UIState<List<Order>>> get() = _state

    init {
        //loadAllOrders()
    }

    // ---  Lógica de Búsqueda (Conectada al botón de lupa) ---
    fun onSearchEntered(search: String) { _search.value = search }
    fun onSearchClicked() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = repository.searchOrders(_search.value)

            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else if (result is Resource.Error) {
                _state.value = UIState(message = result.message?:"An error ocurred")
            }
        }
    }

    // Obtener todas las órdenes
    fun loadAllOrders() {
        viewModelScope.launch {
            repository.getAllOrders()
        }
    }


    // PUT /api/v1/orders/{orderId}/status - Actualizar estado
    /*fun updateOrderStatus(orderId: Int, newStatus: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (repository.updateOrderStatus(orderId, newStatus)) {
                is Resource.Success -> {
                    loadOrderById(orderId)
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(isLoading = false, error = "Error al actualizar estado. Intente de nuevo.")
                    }
                }
            }
        }
    }*/
}
