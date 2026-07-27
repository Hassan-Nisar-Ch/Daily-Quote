package com.example.dailyquote.domain.model

data class Quote(
    val quote: String,
    val author: String,
    val categories: List<String>,
    var isFavorite: Boolean
)