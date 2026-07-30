package com.example.dailyquote.data.remote.api

import com.example.dailyquote.data.remote.dto.QuoteDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/quoteoftheday")
    suspend fun getQuoteOfTheDay(): List<QuoteDto>

    @GET("v2/quotes")
    suspend fun getQuotesByCategory(
        @Query("categories") category: String
    ): List<QuoteDto>
}
