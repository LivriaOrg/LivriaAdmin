package com.example.adminlivria.stockcontext.data.model

import com.google.gson.annotations.SerializedName

data class CreateBookRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("author") val author: String,
    @SerializedName("stock") val stock: Int,
    @SerializedName("cover") val cover: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("language") val language: String
)
