package com.example.adminlivria.bookcontext.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlivria.bookcontext.data.repository.BooksRepository
import com.example.adminlivria.bookcontext.domain.Book
import com.example.adminlivria.bookcontext.domain.BooksStats
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class BooksManagementViewModel(
    private val repository: BooksRepository
) : ViewModel() {

    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search
    fun onSearch(newValue: String) { _search.value = newValue }

    val books: StateFlow<List<Book>> =
        repository.streamBooks(_search.debounce(300))
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val stats: StateFlow<BooksStats> =
        books.map { list ->
            val total = list.size
            val genres = list.map { it.genre }.distinct().size
            val priced = list.map { it.price }.filter { it > 0.0 }
            val avg = if (priced.isNotEmpty()) priced.average() else 0.0
            BooksStats(total, genres, avg, total, total)
        }.stateIn(viewModelScope, SharingStarted.Lazily, BooksStats())

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
            repository.refreshBooks()

    }
}
