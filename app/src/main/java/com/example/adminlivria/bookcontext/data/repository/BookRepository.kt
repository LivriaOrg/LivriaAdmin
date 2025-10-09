package com.example.adminlivria.bookcontext.data.repository

import com.example.adminlivria.bookcontext.data.local.BookDao
import com.example.adminlivria.bookcontext.data.local.toDomain
import com.example.adminlivria.bookcontext.data.local.toEntity
import com.example.adminlivria.bookcontext.data.remote.BookService
import com.example.adminlivria.bookcontext.data.remote.toDomain
import com.example.adminlivria.bookcontext.domain.Book
import kotlinx.coroutines.flow.*

class BooksRepository(
    private val dao: BookDao,
    private val service: BookService
) {
    fun getBooks(): Flow<List<Book>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    // 👇 NUEVO: stream que reacciona al query
    fun streamBooks(queryFlow: Flow<String>): Flow<List<Book>> =
        queryFlow
            .map { it.trim() }
            .distinctUntilChanged()
            .flatMapLatest { q ->
                if (q.isBlank()) dao.getAll() else dao.search(q)
            }
            .map { list -> list.map { it.toDomain() } }

    suspend fun refreshBooks() {
        try {
            val response = service.getAllBooks()
            if (response.isSuccessful) {
                val remoteBooks = response.body().orEmpty().map { it.toDomain() }
                dao.replaceAll(remoteBooks.map { it.toEntity() })
            } else {
                android.util.Log.e("BooksRepo", "HTTP ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            android.util.Log.e("BooksRepo", "refreshBooks error", e)
        }
    }

    suspend fun isEmpty(): Boolean = dao.count() == 0
}
