package com.example.dailyquote.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey val quote: String,
    val author: String,
    val categories: List<String>
)
