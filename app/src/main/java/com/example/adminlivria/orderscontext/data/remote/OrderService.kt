package com.example.adminlivria.orderscontext.data.remote

import OrderDto
import retrofit2.Response
import retrofit2.http.*

interface OrderService {


    @GET("orders")
    suspend fun getAllOrders(): Response<List<OrderDto>>


    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") id: Int): Response<OrderDto>


    @GET("orders/code/{code}")
    suspend fun getOrderByCode(@Path("code") code: String): Response<OrderDto>


    @GET("orders/users/{userClientId}")
    suspend fun getOrdersByClient(
        @Path("userClientId") userClientId: Int
    ): Response<List<OrderDto>>


    @PUT("orders/{orderId}/status")
    suspend fun updateOrderStatus(
        @Path("orderId") orderId: Int,
        @Body statusUpdate: Map<String, String>
    ): Response<Unit>

}