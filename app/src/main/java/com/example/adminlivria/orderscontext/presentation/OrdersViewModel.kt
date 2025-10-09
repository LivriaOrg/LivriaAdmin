package com.example.adminlivria.orderscontext.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlivria.common.Resource
import com.example.adminlivria.orderscontext.data.repository.OrderRepository
import com.example.adminlivria.orderscontext.domain.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


// --- 1. Estado de la UI ---
data class OrdersState(
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val error: String? = null,
    val singleOrder: Order? = null
)

class OrdersViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    var search by mutableStateOf("")
        private set

    fun onSearch(newValue: String) {
        search = newValue
    }

    private val _state = MutableStateFlow(OrdersState())
    val state: StateFlow<OrdersState> = _state.asStateFlow()

    init {
        //loadAllOrders()
    }

    // ---  Lógica de Búsqueda (Conectada al botón de lupa) ---
    fun onSearchClicked() {
        val query = search.trim()

        // Si está vacío, cargamos la lista completa.
        if (query.isBlank()) {
            loadAllOrders()
            return
        }

        // Si es un número, intentamos buscar directamente por ID (más eficiente).
        val orderId = query.toIntOrNull()
        if (orderId != null) {
            loadOrderById(orderId)
            return
        }

        // Si no es un número (es un nombre o código), usamos la búsqueda con filtrado local.
        searchOrdersByString(query)
    }

    // --- 5. Implementación de las Operaciones ---

    // GET /api/v1/orders - Obtener todas las órdenes
    fun loadAllOrders() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, orders = emptyList(), singleOrder = null) }

            when (val result = repository.getAllOrders()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(isLoading = false, orders = result.data ?: emptyList())
                    }
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    // GET /api/v1/orders/{id} - Obtener orden por ID
    fun loadOrderById(orderId: Int) {
        viewModelScope.launch {
            // Limpiar resultados anteriores y mostrar carga
            _state.update { it.copy(isLoading = true, error = null, singleOrder = null, orders = emptyList()) }

            when (val result = repository.getOrderById(orderId)) {
                is Resource.Success -> {
                    // Convertimos el resultado individual en una lista para simplificar la UI de resultados
                    _state.update {
                        it.copy(isLoading = false, orders = listOfNotNull(result.data))
                    }
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    // Búsqueda por Cadena (Nombre de Cliente o Código) con Filtrado Local
    private fun searchOrdersByString(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, orders = emptyList(), singleOrder = null) }

            // Llama al repositorio para traer todo y filtrar por nombre/código
            when (val result = repository.searchOrders(query)) {
                is Resource.Success -> {
                    // Los resultados múltiples se asignan a 'orders'
                    _state.update { it.copy(isLoading = false, orders = result.data ?: emptyList()) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    // GET /api/v1/orders/users/{userClientId} - Obtener órdenes de un usuario (por ID de cliente)
    fun loadOrdersByClient(clientId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, orders = emptyList(), singleOrder = null) }

            when (val result = repository.getOrdersByClient(clientId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(isLoading = false, orders = result.data ?: emptyList())
                    }
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    // PUT /api/v1/orders/{orderId}/status - Actualizar estado
    fun updateOrderStatus(orderId: Int, newStatus: String) {
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
    }
}
