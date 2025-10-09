package com.example.adminlivria.orderscontext.data.remote

import OrderDto
import retrofit2.Response
import retrofit2.http.*

interface OrderService {

    // GET Obtener todas las órdenes
    @GET("orders")
    suspend fun getAllOrders(): Response<List<OrderDto>>

    // GET - Obtener orden por ID
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
    @PUT("orders/{orderId}/status")
    suspend fun updateOrderStatus(
        @Path("orderId") orderId: Int,
        @Body statusUpdate: Map<String, String>
    ): Response<Unit>

}