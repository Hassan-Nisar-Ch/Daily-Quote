package com.example.dailyquote.presentation.categories

import androidx.lifecycle.ViewModel
import com.example.dailyquote.domain.model.Category
import com.example.dailyquote.domain.model.categories
import com.example.dailyquote.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categoriesFlow = _categories.asStateFlow()

    init {
        _categories.value = categories
    }

    fun onCategoryClick(it: Category) {}
}
