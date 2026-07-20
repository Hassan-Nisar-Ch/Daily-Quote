package com.example.dailyquote.data.api

import com.example.dailyquote.data.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("quoteoftheday")
    suspend fun getQuoteOfTheDay(): Response<Quote>
}
