package com.example.dailyquote.data.model

// Network result wrapper
sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val errorMessage: String) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}