package com.example.adminlivria.common

class UIState<T> (
    val isLoading: Boolean = false,
    val data: T? = null,
    val message: String = ""
)