package com.example.adminlivria.common

import androidx.room.TypeConverter
import java.time.Instant

class DateConverter {
    // Convierte Instant a Long (timestamp) para guardar en la DB
    @TypeConverter
    fun fromInstant(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }

    // Convierte Long (timestamp) a Instant al leer de la DB
    @TypeConverter
    fun toInstant(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }
}