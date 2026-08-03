package com.example.dailyquote.presentation.common

sealed class QuoteEvent {
    data class ShareQuote(val quote: String, val author: String) : QuoteEvent()
    data class CopyQuote(val quote: String, val author: String) : QuoteEvent()
    object SaveQuote : QuoteEvent()
    data class ShowMessage(val message: String) : QuoteEvent()
}