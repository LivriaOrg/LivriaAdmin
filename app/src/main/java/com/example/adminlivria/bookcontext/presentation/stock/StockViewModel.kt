package com.example.adminlivria.bookcontext.presentation.stock

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.adminlivria.bookcontext.data.local.AdminDatabase
import com.example.adminlivria.bookcontext.data.repository.BooksRepository
import com.example.adminlivria.bookcontext.domain.Book
import com.example.adminlivria.common.bookServiceInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StockViewModel(
    private val repo: BooksRepository,
    private val bookId: Int
) : ViewModel() {

    val book: StateFlow<Book?> =
        repo.getBookById(bookId)
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _qty = MutableStateFlow(1)
    val qty: StateFlow<Int> = _qty
    fun setQty(newQty: Int) { _qty.value = newQty.coerceAtLeast(1) }

    val totalToPay: StateFlow<Double> =
        book.combine(qty) { b, q -> (b?.purchasePrice ?: 0.0) * q }
            .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun confirm(onDone: () -> Unit) {
        val q = qty.value
        viewModelScope.launch {
            try {
                repo.addStock(bookId, q)
                onDone()
            } catch (_: Exception) {

            }
        }
    }
}

class StockViewModelFactory(
    context: Context,
    private val bookId: Int
) : ViewModelProvider.Factory {
    private val db = AdminDatabase.getInstance(context)
    private val repo = BooksRepository(db.bookDao(), bookServiceInstance)
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        StockViewModel(repo, bookId) as T
}
