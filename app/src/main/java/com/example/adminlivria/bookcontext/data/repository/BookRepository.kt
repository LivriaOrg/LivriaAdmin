package com.example.adminlivria.bookcontext.data.repository

import com.example.adminlivria.bookcontext.data.local.BookDao
import com.example.adminlivria.bookcontext.data.local.toDomain
import com.example.adminlivria.bookcontext.data.local.toEntity
import com.example.adminlivria.bookcontext.data.remote.BookService
import com.example.adminlivria.bookcontext.data.remote.toDomain
import com.example.adminlivria.bookcontext.domain.Book
import kotlinx.coroutines.flow.*
import com.example.adminlivria.statscontext.domain.BookInfoSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BooksRepository(
    private val dao: BookDao,
    private val service: BookService
) : BookInfoSource {
    fun getBooks(): Flow<List<Book>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }


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

    suspend fun insertBook(book: Book) {
        dao.upsertAll(listOf(book.toEntity()))
    }
    fun getBookById(id: Int): Flow<Book?> =
        dao.getById(id).map { it?.toDomain() }
    suspend fun isEmpty(): Boolean = dao.count() == 0

    override suspend fun getTopBooksByStock(limit: Int): List<Book> {
        val entities = dao.getTopByStock(limit)
        return entities.map { it.toDomain() }
    }

    override suspend fun getGenreInventoryValue(): Map<String, Float> {
        return withContext(Dispatchers.IO) {
            val genreMonetaryValues = dao.getGenreMonetaryValue()

            genreMonetaryValues.associate {
                it.genre to it.total_monetary_value.toFloat()
            }
        }
    }

    override suspend fun getGenreInventoryCount(): Map<String, Int> {
        val counts = dao.countBooksByGenre()
        return counts.associate { it.genre to it.count }
    }
}
