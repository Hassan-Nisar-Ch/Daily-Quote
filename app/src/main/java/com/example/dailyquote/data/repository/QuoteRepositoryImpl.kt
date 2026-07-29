package com.example.dailyquote.data.repository

import com.example.dailyquote.data.local.dao.QuoteDao
import com.example.dailyquote.data.mapper.toDomain
import com.example.dailyquote.data.mapper.toEntity
import com.example.dailyquote.data.remote.api.ApiService
import com.example.dailyquote.domain.model.Quote
import com.example.dailyquote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val quoteDao: QuoteDao
) : QuoteRepository {

    override suspend fun getQuoteOfTheDay(): Flow<List<Quote>> = flow {
        val quotes = apiService.getQuoteOfTheDay()
        emit(quotes.map { it.toDomain() })
    }

    override suspend fun getQuotesByCategory(category: String): Flow<List<Quote>> = flow {
        val quotes = apiService.getQuotesByCategory(category)
        emit(quotes.map { it.toDomain() })
    }

    override fun getFavoriteQuotes(): Flow<List<Quote>> {
        return quoteDao.getFavoriteQuotes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertQuote(quote: Quote) {
        quoteDao.insertQuote(quote.toEntity())
    }

    override suspend fun deleteQuote(quote: Quote) {
        quoteDao.deleteQuote(quote.toEntity())
    }

    override fun isFavorite(quote: String): Flow<Boolean> {
        return quoteDao.isFavorite(quote)
    }

}