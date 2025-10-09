package com.example.adminlivria.orderscontext.data.remote

import OrderDto
import retrofit2.Response
import retrofit2.http.*

interface OrderService {

    // 1. POST - Crear una nueva orden
    @POST("orders")
    suspend fun createOrder(@Body orderDto: OrderDto): Response<OrderDto>

    // 2. GET Obtener todas las órdenes
    @GET("orders")
    suspend fun getAllOrders(): Response<List<OrderDto>>

    // 3. GET - Obtener orden por ID
    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") id: Int): Response<OrderDto>

    // 4. GET - Obtener orden por Código
    @GET("orders/code/{code}")
    suspend fun getOrderByCode(@Path("code") code: String): Response<OrderDto>

    // 5. GET - Obtener órdenes de un usuario
    @GET("orders/users/{userClientId}")
    suspend fun getOrdersByClient(
        @Path("userClientId") userClientId: Int
    ): Response<List<OrderDto>>

    // 6. PUT orders/{orderId}/status - Actualizar el estado de una orden
    // Asumimos que el body es un simple objeto que contiene el nuevo estado (ej: {"status": "DELIVERED"})
    @PUT("orders/{orderId}/status")
    suspend fun updateOrderStatus(
        @Path("orderId") orderId: Int,
        @Body statusUpdate: Map<String, String> // Un mapa para enviar {"status": "NUEVO_ESTADO"}
    ): Response<Unit> // La respuesta no necesita un cuerpo específico (Unit)

}