package com.example.dailyquote.presentation.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquote.domain.model.Quote
import com.example.dailyquote.domain.repository.QuoteRepository
import com.example.dailyquote.presentation.home.QuoteEvent
import com.google.android.material.card.MaterialCardView
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
class QuotesViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes: StateFlow<List<Quote>> = _quotes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _event = MutableSharedFlow<QuoteEvent>()
    val event: SharedFlow<QuoteEvent> = _event.asSharedFlow()

    fun getQuotesByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                quoteRepository.getQuotesByCategory(category).collect { result ->
                    _quotes.value = result
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
                _isLoading.value = false
            }
        }
    }

    fun onShareClicked(quote: Quote) {
        viewModelScope.launch {
            _event.emit(QuoteEvent.ShareQuote(quote.quote, quote.author))
        }
    }

    fun onCopyClicked(quote: Quote) {
        viewModelScope.launch {
            _event.emit(QuoteEvent.CopyQuote(quote.quote, quote.author))
        }
    }

    fun onFavoriteClicked(quote: Quote) {
        viewModelScope.launch {
            quoteRepository.isFavorite(quote.quote).collect { isFavorite ->
                if (isFavorite) {
                    quoteRepository.deleteQuote(quote)
                    _event.emit(QuoteEvent.ShowMessage("Quote removed from favorites"))
                } else {
                    quoteRepository.insertQuote(quote)
                    _event.emit(QuoteEvent.ShowMessage("Quote added to favorites"))
                }
            }
        }
    }

    fun onSaveClicked(view: MaterialCardView) {
        viewModelScope.launch {
            _event.emit(QuoteEvent.SaveQuote(view))
        }
    }
}
