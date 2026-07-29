package com.example.dailyquote.presentation.home

import com.google.android.material.card.MaterialCardView

sealed class QuoteEvent {
    data class ShareQuote(val quote: String, val author: String) : QuoteEvent()
    data class CopyQuote(val quote: String, val author: String) : QuoteEvent()
    data class SaveQuote(val view: MaterialCardView) : QuoteEvent()
    data class ShowMessage(val message: String) : QuoteEvent()
}