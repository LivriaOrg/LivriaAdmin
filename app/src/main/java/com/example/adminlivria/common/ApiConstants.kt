package com.example.adminlivria.common

import com.example.adminlivria.profilecontext.data.remote.AuthService
import android.content.Context
import com.example.adminlivria.profilecontext.data.local.TokenManager
import com.example.adminlivria.profilecontext.data.remote.UserAdminService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://livria-api.azurewebsites.net/api/v1/"

private lateinit var tokenManager: TokenManager

fun initializeTokenManager(context: Context) {
    tokenManager = TokenManager(context)
}


private fun createOkHttpClient(): OkHttpClient {

    val authInterceptor = okhttp3.Interceptor { chain ->
        val originalRequest = chain.request()

        val token = tokenManager.getToken()

        val newRequest = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        chain.proceed(newRequest)
    }

    return OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()
}


private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val authServiceInstance: AuthService by lazy {
    retrofit.create(AuthService::class.java)
}

val userAdminServiceInstance: UserAdminService by lazy {
    retrofit.create(UserAdminService::class.java)
}
