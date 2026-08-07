package com.example.blinkit_clone.Profile

import androidx.lifecycle.ViewModel
import com.example.blinkit_clone.data.ProductRepository
import com.example.blinkit_clone.data.model.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OrderAgainViewModel @Inject constructor(
    // The ViewModel now gets the repository via dependency injection.
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _productItems = MutableStateFlow<List<ProductItem>>(emptyList())
    val productItems = _productItems.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        // The ViewModel asks the repository for the data
        // instead of having it hardcoded here.
        _productItems.value = productRepository.getOrderAgainProducts()
    }
}

