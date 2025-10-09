package com.example.adminlivria.statscontext.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StatsDao {

    @Query("SELECT * FROM stats_cache WHERE id = 1")
    fun getStatsCache(): Flow<StatEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: StatEntity)

    @Query("DELETE FROM stats_cache")
    suspend fun clearCache()
}