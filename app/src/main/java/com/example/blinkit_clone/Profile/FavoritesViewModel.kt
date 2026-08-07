package com.example.blinkit_clone.Profile

import androidx.lifecycle.ViewModel
import com.example.blinkit_clone.R
import com.example.blinkit_clone.data.model.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {

    private val _favoriteProducts = MutableStateFlow<List<ProductItem>>(emptyList())
    val favoriteProducts = _favoriteProducts.asStateFlow()

    init {
        loadFavoriteProducts()
    }

    private fun loadFavoriteProducts() {
        // In a real app, this data would come from a user's saved list.
        // For now, we are just moving the hardcoded list here.
        _favoriteProducts.value = listOf(
            ProductItem(R.drawable.milk, "Mother Dairy Classic Curd", "10 MINS", "400 g", emptyList(), 0, 36.0, 38.0, "5% OFF"),
            ProductItem(R.drawable.bread, "Whole Wheat Bread", "10 MINS", "400g", emptyList(), 0, 45.0, 48.0, "5% OFF"),
            ProductItem(R.drawable.choclate, "Cadbury Dairy Milk", "12 MINS", "50 g", emptyList(), 0, 40.0, 44.0, "10% OFF")
        )
    }
}
