package com.example.adminlivria.bookcontext.data.remote


import retrofit2.Response
import retrofit2.http.GET

interface BookService {
    @GET("books")
    suspend fun getAllBooks(): Response<List<BookDto>>
}