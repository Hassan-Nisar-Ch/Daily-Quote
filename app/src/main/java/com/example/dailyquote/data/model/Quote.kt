package com.example.dailyquote.data.model

data class Quote(
    val quote: String,
    val author: String,
    val categories: List<String>
)