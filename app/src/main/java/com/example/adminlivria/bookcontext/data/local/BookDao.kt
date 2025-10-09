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

    // Reemplaza todo en una transacción
    @androidx.room.Transaction
    suspend fun replaceAll(entities: List<BookEntity>) {
        clearAll()
        upsertAll(entities)
    }
}