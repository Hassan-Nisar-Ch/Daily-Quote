package com.example.dailyquote.data.remote.dto

data class QuoteDto(
    val quote: String,
    val author: String,
    val categories: List<String>
)