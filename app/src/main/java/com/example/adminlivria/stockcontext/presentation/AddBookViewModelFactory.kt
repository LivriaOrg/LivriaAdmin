package com.example.adminlivria.stockcontext.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adminlivria.stockcontext.data.remote.InventoryService
import java.lang.IllegalArgumentException


class AddBookViewModelFactory(
    private val inventoryService: InventoryService,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddBookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddBookViewModel(inventoryService, context.applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
