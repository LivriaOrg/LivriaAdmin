package com.example.adminlivria.data.model

import com.google.gson.annotations.SerializedName


data class AuthResponse(
    @SerializedName("token")
    val token: String?,

    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("identityId")
    val id: Int?,

    @SerializedName("userId")
    val userId: Int?,

    @SerializedName("username")
    val userName: String?
)