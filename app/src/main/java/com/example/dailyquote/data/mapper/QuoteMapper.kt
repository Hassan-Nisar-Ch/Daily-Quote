package com.example.dailyquote.data.mapper

import com.example.dailyquote.data.local.entity.QuoteEntity
import com.example.dailyquote.data.remote.dto.QuoteDto
import com.example.dailyquote.domain.model.Quote

fun Quote.toEntity(): QuoteEntity =
    QuoteEntity(
        quote = quote,
        author = author,
        categories = categories
    )

fun QuoteEntity.toDomain(): Quote =
    Quote(
        quote = quote,
        author = author,
        categories = categories
    )

fun QuoteDto.toDomain(): Quote =
    Quote(
        quote = quote,
        author = author,
        categories = categories
    )

