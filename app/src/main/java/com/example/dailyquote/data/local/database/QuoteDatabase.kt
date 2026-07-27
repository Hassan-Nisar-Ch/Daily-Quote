package com.example.dailyquote.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dailyquote.data.local.converter.Converters
import com.example.dailyquote.data.local.dao.QuoteDao
import com.example.dailyquote.data.local.entity.QuoteEntity

@Database(entities = [QuoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}