package com.example.adminlivria.bookcontext.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adminlivria.common.data.local.AdminDatabase
import com.example.adminlivria.bookcontext.data.repository.BooksRepository
import com.example.adminlivria.common.bookServiceInstance

class BooksViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AdminDatabase.getInstance(context).bookDao()
        val repository = BooksRepository(dao, bookServiceInstance)
        return BooksManagementViewModel(repository) as T
    }
}
