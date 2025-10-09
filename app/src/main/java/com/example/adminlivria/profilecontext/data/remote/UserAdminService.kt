package com.example.adminlivria.data.remote

import com.example.adminlivria.data.model.UserAdminDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserAdminService {

    @GET("useradmins")
    suspend fun getUserAdminData(): Response<List<UserAdminDto>>

    @PUT("useradmins/{id}")
    suspend fun updateUserAdmin(
        @Path("id") id: Int,
        @Body userAdmin: UserAdminDto
    ): Response<Unit>
}
