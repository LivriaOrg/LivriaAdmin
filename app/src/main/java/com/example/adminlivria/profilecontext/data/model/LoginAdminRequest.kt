package com.example.adminlivria.profilecontext.data.model

import com.google.gson.annotations.SerializedName

data class LoginAdminRequest(
    @SerializedName("Username")
    val username: String,

    @SerializedName("Password")
    val password: String,

    @SerializedName("SecurityPin")
    val securityPin: String
)