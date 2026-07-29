package com.example.dailyquote.data.remote.api

import com.example.dailyquote.BuildConfig
import com.example.dailyquote.data.remote.dto.QuoteDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    @GET("v2/quoteoftheday")
    suspend fun getQuoteOfTheDay(): List<QuoteDto>

    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    @GET("v2/quotes")
    suspend fun getQuotesByCategory(
        @Query("categories") category: String
    ): List<QuoteDto>
}
