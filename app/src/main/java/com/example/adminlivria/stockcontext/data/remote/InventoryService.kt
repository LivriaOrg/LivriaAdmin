package com.example.adminlivria.stockcontext.data.remote

import com.example.adminlivria.stockcontext.data.model.CreateBookRequest
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


data class BookResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("author") val author: String,
    @SerializedName("stock") val stock: Int,
    @SerializedName("cover") val cover: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("language") val language: String
)


data class UpdateStockRequest(
    @SerializedName("quantityToAdd") val quantityToAdd: Int
)




interface InventoryService {

    @POST("books")
    suspend fun createBook(
        @Body request: CreateBookRequest
    ): Response<BookResponse>

    @GET("books")
    suspend fun getAllBooks(): Response<List<BookResponse>>

    @GET("books/{id}")
    suspend fun getBookById(
        @Path("id") bookId: Int
    ): Response<BookResponse>

    @PUT("books/{bookId}/stock")
    suspend fun updateBookStock(
        @Path("bookId") bookId: Int,
        @Body request: UpdateStockRequest
    ): Response<BookResponse>
}
