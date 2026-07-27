package com.example.dailyquote.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquote.domain.model.Quote
import com.example.dailyquote.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _quote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> = _quote.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    init {
        getQuoteOfTheDay()
    }

    fun getQuoteOfTheDay() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                quoteRepository.getQuoteOfTheDay().collect { quotes ->
                    _quote.value = quotes.firstOrNull()
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
                _isLoading.value = false
            }
        }
    }

    fun onShareClicked() {
        val currentQuote = _quote.value
        if (currentQuote != null) {
            viewModelScope.launch {
                _event.emit(HomeEvent.ShareQuote(currentQuote.quote, currentQuote.author))
            }
        }
    }

    fun onCopyClicked() {
        val currentQuote = _quote.value
        if (currentQuote != null) {
            viewModelScope.launch {
                _event.emit(HomeEvent.CopyQuote(currentQuote.quote, currentQuote.author))
            }
        }
    }

    sealed class HomeEvent {
        data class ShareQuote(val quote: String, val author: String) : HomeEvent()
        data class CopyQuote(val quote: String, val author: String) : HomeEvent()
    }
}
