package com.example.adminlivria.data.remote

import com.example.adminlivria.data.model.AuthResponse
import com.example.adminlivria.data.model.LoginAdminRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {

    @POST("authentication/sign-in/admin")
    suspend fun signInAdmin(
        @Body request: LoginAdminRequest
    ): Response<AuthResponse>

}