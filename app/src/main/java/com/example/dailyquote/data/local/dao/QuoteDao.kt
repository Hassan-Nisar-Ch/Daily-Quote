package com.example.dailyquote.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailyquote.data.local.entity.QuoteEntity

@Dao
interface QuoteDao {

    @Insert
    suspend fun insertQuote(quote: QuoteEntity)

    @Query("SELECT * FROM quotes WHERE isFavorite = 1")
    suspend fun getFavoriteQuotes(): List<QuoteEntity>

}