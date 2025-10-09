package com.example.adminlivria.bookcontext.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM books ORDER BY title")
    fun getAll(): Flow<List<BookEntity>>

    @Query("""
        SELECT * FROM books
        WHERE title LIKE '%' || :query || '%'
           OR author LIKE '%' || :query || '%'
        ORDER BY title
    """)
    fun search(query: String): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(entities: List<BookEntity>)

    @Query("SELECT COUNT(*) FROM books")
    suspend fun count(): Int

    @Query("DELETE FROM books")
    suspend fun clearAll()

    @Query("SELECT * FROM books WHERE id = :id")
    fun getById(id: Int): Flow<BookEntity?>


    @androidx.room.Transaction
    suspend fun replaceAll(entities: List<BookEntity>) {
        clearAll()
        upsertAll(entities)
    }

    @Query("SELECT * FROM books ORDER BY stock DESC LIMIT :limit")
    suspend fun getTopByStock(limit: Int): List<BookEntity>

    @Query("""
        SELECT genre, SUM(stock) as count
        FROM books
        GROUP BY genre
        ORDER BY count DESC
    """)
    suspend fun countBooksByGenre(): List<GenreCount>

    data class GenreCount(
        val genre: String,
        val count: Int
    )

    @Query("SELECT genre, SUM(stock * price) AS total_monetary_value FROM books GROUP BY genre")
    fun getGenreMonetaryValue(): List<GenreMonetaryValue>

    data class GenreMonetaryValue(
        val genre: String,
        val total_monetary_value: Double
    )
}
