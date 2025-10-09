package com.example.adminlivria.data.model

import com.google.gson.annotations.SerializedName


data class UserAdminDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("display")
    val display: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("adminAccess")
    val adminAccess: Boolean,

    @SerializedName("securityPin")
    val securityPin: String,

    @SerializedName("capital") val capital: Double

)