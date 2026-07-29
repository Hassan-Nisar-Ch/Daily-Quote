package com.example.dailyquote.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dailyquote.data.local.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert
    suspend fun insertQuote(quote: QuoteEntity)

    @Delete
    suspend fun deleteQuote(quote: QuoteEntity)

    @Query("SELECT * FROM quotes")
    fun getFavoriteQuotes(): Flow<List<QuoteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM quotes WHERE quote = :quote)")
    fun isFavorite(quote: String): Flow<Boolean>

}