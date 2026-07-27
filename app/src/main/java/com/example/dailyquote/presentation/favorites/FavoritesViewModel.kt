package com.example.dailyquote.presentation.favorites

import androidx.lifecycle.ViewModel
import com.example.dailyquote.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {
}
