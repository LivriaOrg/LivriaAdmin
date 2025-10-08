package com.example.adminlivria.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://10.0.2.2:5119/api/v1/"

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val authServiceInstance: AuthService by lazy {
    provideRetrofit().create(AuthService::class.java)
}