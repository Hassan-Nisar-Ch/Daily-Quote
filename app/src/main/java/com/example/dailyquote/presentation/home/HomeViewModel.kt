package com.example.dailyquote.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquote.domain.model.Quote
import com.example.dailyquote.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _rawQuote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> = combine(_rawQuote, quoteRepository.getFavoriteQuotes()) { raw, favorites ->
        raw?.copy(isFavorite = favorites.any { it.quote == raw.quote })
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val currentQuote: Quote? get() = quote.value

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _event = MutableSharedFlow<QuoteEvent>()
    val event: SharedFlow<QuoteEvent> = _event.asSharedFlow()

    init {
        getQuoteOfTheDay()
    }

    fun getQuoteOfTheDay() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                quoteRepository.getQuoteOfTheDay().collect { quotes ->
                    _rawQuote.value = quotes.firstOrNull()
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
                _isLoading.value = false
            }
        }
    }


    fun onShareClicked() {
        currentQuote?.let { quote ->
            viewModelScope.launch {
                _event.emit(QuoteEvent.ShareQuote(quote.quote, quote.author))
            }
        }
    }

    fun onCopyClicked() {
        currentQuote?.let { quote ->
            viewModelScope.launch {
                _event.emit(QuoteEvent.CopyQuote(quote.quote, quote.author))
            }
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            _event.emit(QuoteEvent.SaveQuote)
        }
    }

    fun onFavoriteClicked() {
        currentQuote?.let { quote ->
            viewModelScope.launch {
                if (quote.isFavorite) {
                    quoteRepository.deleteQuote(quote)
                    _event.emit(QuoteEvent.ShowMessage("Quote removed from favorites"))
                } else {
                    quoteRepository.insertQuote(quote)
                    _event.emit(QuoteEvent.ShowMessage("Quote added to favorites"))
                }
            }
        }
    }
}
