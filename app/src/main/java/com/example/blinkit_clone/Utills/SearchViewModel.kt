package com.example.blinkit_clone.Utills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blinkit_clone.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    // This flow automatically updates the search results whenever the search text changes.
    // The 'debounce' operator adds a small delay to prevent searching on every single keystroke.
    val searchResults = searchText
        .debounce(300L) // 300ms delay
        .map { query ->
            if (query.isBlank()) {
                emptyList()
            } else {
                productRepository.searchProducts(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

