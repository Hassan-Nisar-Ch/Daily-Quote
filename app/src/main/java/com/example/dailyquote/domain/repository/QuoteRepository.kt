package com.example.dailyquote.domain.repository

import com.example.dailyquote.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun getQuoteOfTheDay(): Flow<List<Quote>>
}
