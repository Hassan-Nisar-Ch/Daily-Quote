package com.example.dailyquote.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquote.domain.model.Category
import com.example.dailyquote.domain.model.categories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categoriesFlow = _categories.asStateFlow()

    private val _event = MutableSharedFlow<CategoriesEvent>()
    val event: SharedFlow<CategoriesEvent> = _event.asSharedFlow()

    init {
        _categories.value = categories
    }

    fun onCategoryClick(apiName: String, displayName: String) {
        viewModelScope.launch {
            _event.emit(CategoriesEvent.NavigateToQuotes(apiName, displayName))
        }
    }

    sealed class CategoriesEvent {
        data class NavigateToQuotes(val category: String, val displayName: String) : CategoriesEvent()
    }
}
