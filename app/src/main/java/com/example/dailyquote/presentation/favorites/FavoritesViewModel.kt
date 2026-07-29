package com.example.dailyquote.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquote.domain.model.Quote
import com.example.dailyquote.domain.repository.QuoteRepository
import com.example.dailyquote.presentation.home.QuoteEvent
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    val favorites = quoteRepository.getFavoriteQuotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _event = MutableSharedFlow<QuoteEvent>()
    val event: SharedFlow<QuoteEvent> = _event.asSharedFlow()

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

    fun onSaveClicked(cardView: MaterialCardView) {
        viewModelScope.launch {
            _event.emit(QuoteEvent.SaveQuote(cardView))
        }
    }

    fun onFavoriteClicked(quote: Quote) {
        viewModelScope.launch {
            quoteRepository.deleteQuote(quote)
            _event.emit(QuoteEvent.ShowMessage("Removed from favorites"))
        }
    }
}
