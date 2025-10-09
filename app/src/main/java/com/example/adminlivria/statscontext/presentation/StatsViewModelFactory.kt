package com.example.adminlivria.statscontext.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adminlivria.common.data.local.AdminDatabase
import com.example.adminlivria.bookcontext.data.repository.BooksRepository
import com.example.adminlivria.statscontext.data.repository.StatsRepository
import com.example.adminlivria.bookcontext.data.remote.BookService
import com.example.adminlivria.common.bookServiceInstance

class StatsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val database = AdminDatabase.getInstance(context)
        val statsDao = database.statsDao()
        val bookDao = database.bookDao()

        val booksRepositoryInstance = BooksRepository(
            dao = bookDao,
            service = bookServiceInstance
        )

        val statsRepository = StatsRepository(
            dao = statsDao,
            bookInfoSource = booksRepositoryInstance
        )

        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            return StatsViewModel(statsRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}