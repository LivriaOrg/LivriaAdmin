package com.example.adminlivria.orderscontext.data.repository

import OrderDto
import com.example.adminlivria.common.Resource
import com.example.adminlivria.orderscontext.data.local.OrderDao
import com.example.adminlivria.orderscontext.data.remote.OrderService
import com.example.adminlivria.orderscontext.domain.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import toDomain

class OrderRepository(
    private val service: OrderService,
    private val dao: OrderDao
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
            return@withContext Resource.Error(response.errorBody()?.string() ?: "No orders available")
        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: "Error de red")
        }
    }

    // PUT /orders/{orderId}/status - Actualizar el estado de una orden
    /*suspend fun updateOrderStatus(orderId: Int, newStatus: String): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            // Cuerpo de la petición: {"status": "NUEVO_ESTADO"}
            val statusUpdateBody = mapOf("status" to newStatus)
            val response = service.updateOrderStatus(orderId, statusUpdateBody)

            if (response.isSuccessful) {
                return@withContext Resource.Success(data = Unit)
            }
            return@withContext Resource.Error(response.errorBody()?.string() ?: "Error updating order status")
        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: "Error de red al actualizar estado")
        }
    }*/

    // SEARCH
    suspend fun searchOrders(input: String): Resource<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val response = service.getAllOrders()

            if (response.isSuccessful) {
                response.body()?.let { ordersDto: List<OrderDto> ->
                    val filteredOrders = ordersDto
                        .filter { it.userFullName.contains(input, ignoreCase = true) || it.code.contains(input, ignoreCase = true) }
                        .map { it.toDomain() } // Convertir todos a entidades de Dominio

                    if (filteredOrders.isNotEmpty()) {
                        return@withContext Resource.Success(data = filteredOrders)
                    }

                    return@withContext Resource.Error(
                        message = "No se encontraron órdenes para '$input'"
                    )
                }
                return@withContext Resource.Error(message = "El servidor devolvió una lista vacía.")
            }
            return@withContext Resource.Error(message = "Error de API al buscar órdenes: ${response.message()}")

        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: "Error de red al buscar órdenes")
        }
    }


}