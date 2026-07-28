package com.example.dailyquote.data.remote.api

import com.example.dailyquote.data.model.NetworkResult
import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error("Response body is null")
            }
        } else {
            val errorMsg = response.errorBody()?.string() ?: "Unknown error occurred"
            NetworkResult.Error(errorMsg)
        }
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "Unknown error occurred")
    }
}