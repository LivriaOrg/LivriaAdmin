package com.example.adminlivria.statscontext.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stats_cache")
data class StatEntity(
    @PrimaryKey val id: Int = 1,

    val topBooksJson: String = "",
    val genreCountsJson: String = "",
    val capitalFlowJson: String = "",

    val lastUpdated: Long = System.currentTimeMillis()
)