package com.example.dailyquote.data.local.converter

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromCategoriesList(categories: List<String>): String {
        return categories.joinToString(",")
    }

    @TypeConverter
    fun toCategoriesList(data: String): List<String> {
        return if (data.isEmpty()) emptyList() else data.split(",")
    }
}
