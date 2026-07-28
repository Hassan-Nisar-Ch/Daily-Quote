package com.example.dailyquote.domain.repository

import com.example.dailyquote.data.model.NetworkResult
import com.example.dailyquote.data.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getQuoteOfTheDay(): Flow<NetworkResult<Quote>>
}