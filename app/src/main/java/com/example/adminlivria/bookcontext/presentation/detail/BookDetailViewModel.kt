package com.example.adminlivria.bookcontext.presentation.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.adminlivria.common.data.local.AdminDatabase
import com.example.adminlivria.bookcontext.data.repository.BooksRepository
import com.example.adminlivria.bookcontext.domain.Book
import com.example.adminlivria.common.bookServiceInstance
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class BookDetailViewModel(
    private val repository: BooksRepository,
    private val bookId: Int
) : ViewModel() {
    val book: StateFlow<Book?> =
        repository.getBookById(bookId)
            .stateIn(viewModelScope, SharingStarted.Lazily, null)
}

class BookDetailViewModelFactory(
    context: Context,
    private val bookId: Int
) : ViewModelProvider.Factory {
    private val db = AdminDatabase.getInstance(context)
    private val repo = BooksRepository(db.bookDao(), bookServiceInstance) // <- tu instance común
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookDetailViewModel(repo, bookId) as T
    }
}