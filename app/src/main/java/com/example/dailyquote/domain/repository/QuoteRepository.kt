package com.example.dailyquote.domain.repository

import com.example.dailyquote.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun getQuoteOfTheDay(): Flow<List<Quote>>
    suspend fun getQuotesByCategory(category: String): Flow<List<Quote>>
    fun getFavoriteQuotes(): Flow<List<Quote>>
    suspend fun insertQuote(quote: Quote)
    suspend fun deleteQuote(quote: Quote)
    fun isFavorite(quote: String): Flow<Boolean>
}
