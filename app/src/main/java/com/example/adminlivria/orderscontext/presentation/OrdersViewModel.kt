package com.example.adminlivria.orderscontext.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class OrdersViewModel : ViewModel() {

    var search by mutableStateOf("")
        private set

    fun onSearch(newValue: String) {
        search = newValue
    }

}

