package com.example.adminlivria.presentation.ordermanagement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class OrderManagementViewModel : ViewModel() {

    var search by mutableStateOf("")
        private set

    fun onSearch(newValue: String) {
        search = newValue
    }

}