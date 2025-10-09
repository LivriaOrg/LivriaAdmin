package com.example.adminlivria.orderscontext.data.repository

import com.example.adminlivria.common.Resource
import com.example.adminlivria.orderscontext.data.local.OrderDao
import com.example.adminlivria.orderscontext.data.local.toEntity
import com.example.adminlivria.orderscontext.data.local.toItemEntity
import com.example.adminlivria.orderscontext.data.remote.OrderService
import com.example.adminlivria.orderscontext.domain.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import toDomain

class OrderRepository(
    private val service: OrderService,
    private val dao: OrderDao // Para uso local (Room)
) {

    // GET /orders - Obtener todas las órdenes
    suspend fun getAllOrders(): Resource<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val response = service.getAllOrders()
            if (response.isSuccessful) {
                response.body()?.let { listDto ->
                    val orders = listDto.map { it.toDomain() }
                    return@withContext Resource.Success(data = orders)
                }
            }
            return@withContext Resource.Error(response.errorBody()?.string() ?: "Lista de órdenes vacía")
        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: "Error de red al obtener órdenes")
        }
    }

    // GET /orders/{id} - Obtener orden por ID
    suspend fun getOrderById(id: Int): Resource<Order> = withContext(Dispatchers.IO) {
        try {
            val response = service.getOrderById(id)
            if (response.isSuccessful) {
                response.body()?.let { orderDto ->
                    return@withContext Resource.Success(data = orderDto.toDomain())
                }
            }
            return@withContext Resource.Error(response.errorBody()?.string() ?: "Orden no encontrada")
        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: "Error de red al obtener orden por ID")
        }
    }

    // GET /orders/code/{code} - Obtener orden por Código
    suspend fun getOrderByCode(code: String): Resource<Order> = withContext(Dispatchers.IO) {
        try {
            val response = service.getOrderByCode(code)
            if (response.isSuccessful) {
                response.body()?.let { orderDto ->
                    return@withContext Resource.Success(data = orderDto.toDomain())
                }
            }
            return@withContext Resource.Error(response.errorBody()?.string() ?: "Orden no encontrada por código")
        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: "Error de red al obtener orden por código")
        }
    }

    // GET /orders/users/{userClientId} - Obtener órdenes de un usuario
    suspend fun getOrdersByClient(userClientId: Int): Resource<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val response = service.getOrdersByClient(userClientId)
            if (response.isSuccessful) {
                response.body()?.let { listDto ->
                    val orders = listDto.map { it.toDomain() }
                    return@withContext Resource.Success(data = orders)
                }
            }
            return@withContext Resource.Error(response.errorBody()?.string() ?: "Órdenes de cliente no encontradas")
        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: "Error de red al obtener órdenes de cliente")
        }
    }

    // PUT /orders/{orderId}/status - Actualizar el estado de una orden
    suspend fun updateOrderStatus(orderId: Int, newStatus: String): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            // Cuerpo de la petición: {"status": "NUEVO_ESTADO"}
            val statusUpdateBody = mapOf("status" to newStatus)
            val response = service.updateOrderStatus(orderId, statusUpdateBody)

            if (response.isSuccessful) {
                return@withContext Resource.Success(data = Unit)
            }
            return@withContext Resource.Error(response.errorBody()?.string() ?: "Error al actualizar estado")
        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: "Error de red al actualizar estado")
        }
    }

    // SEARCH
    suspend fun searchOrders(query: String): Resource<List<Order>> = withContext(Dispatchers.IO) {
        val lowerCaseQuery = query.lowercase().trim()

        try {
            // 1. Llama al endpoint que trae TODAS las órdenes
            val response = service.getAllOrders()

            if (response.isSuccessful) {
                response.body()?.let { listDto ->

                    // 2. Mapeo DTO -> Dominio y Filtrado Local
                    val filteredOrders = listDto
                        .map { it.toDomain() } // Convertir todos a entidades de Dominio
                        .filter { order ->
                            // El filtro buscará la coincidencia en el ID (si es número) o en el nombre
                            order.userFullName.lowercase().contains(lowerCaseQuery) ||
                                    order.code.lowercase() == lowerCaseQuery
                            // No filtramos por ID aquí, ya que el ViewModel lo maneja primero
                        }

                    if (filteredOrders.isNotEmpty()) {
                        return@withContext Resource.Success(data = filteredOrders)
                    }

                    return@withContext Resource.Error(
                        message = "No se encontraron órdenes para '$query'"
                    )
                }
                return@withContext Resource.Error(message = "El servidor devolvió una lista vacía.")
            }
            return@withContext Resource.Error(message = "Error de API al buscar órdenes: ${response.message()}")

        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: "Error de red al buscar órdenes")
        }
    }


    // Función para guardar una Order completa en Room
    suspend fun saveOrderLocally(order: Order) = withContext(Dispatchers.IO) {
        // 1. Mapeo a OrderEntity y obtención de ID
        val orderEntity = order.toEntity()
        val orderId = dao.insertOrder(orderEntity).toInt()

        // 2. Mapeo e inserción de los ítems
        order.items.forEach { item ->
            val itemEntity = item.toItemEntity(orderId)
            dao.insertItem(itemEntity)
        }
    }
}