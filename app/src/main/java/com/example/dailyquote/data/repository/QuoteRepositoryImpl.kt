package com.example.dailyquote.data.repository

import com.example.dailyquote.data.model.NetworkResult
import com.example.dailyquote.data.model.Quote
import com.example.dailyquote.data.remote.api.ApiService
import com.example.dailyquote.data.remote.api.safeApiCall
import com.example.dailyquote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : QuoteRepository {

    override fun getQuoteOfTheDay(): Flow<NetworkResult<List<Quote>>> = flow {
        emit(NetworkResult.Loading)
        val result = safeApiCall { apiService.getQuoteOfTheDay() }
        emit(result)
    }

    // Note: We don't use flowOn(ioDispatcher) here since Retrofit already handles IO dispatching internally

}