package com.example.blinkit_clone.Utills


import androidx.lifecycle.ViewModel
import com.example.blinkit_clone.R
import com.example.blinkit_clone.presentation.screens.CategoryScreen.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OrderAgainViewModel @Inject constructor() : ViewModel() {

    private val _productItems = MutableStateFlow<List<ProductItem>>(emptyList())
    val productItems = _productItems.asStateFlow()

    init {
        loadProducts()
    }

    // In a real app, this function would fetch data from a database or network.
    private fun loadProducts() {
        _productItems.value = listOf(
            ProductItem(R.drawable.kitchen, "Kitchen Appliances", "11 MINS", "100 g", listOf("Fiber Rich"), 0, "₹39", "₹49", "20% OFF"),
            ProductItem(R.drawable.began, "Began", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, "₹39", "₹51", "23% OFF"),
            ProductItem(R.drawable.cabbage, "Cabbage", "11 MINS", "100 g", emptyList(), 0, "₹39", "₹49", "20% OFF"),
            ProductItem(R.drawable.carrot, "Carrot", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, "₹39", "₹51", "23% OFF"),
            ProductItem(R.drawable.capcicum, "Capsicum", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, "₹39", "₹51", "23% OFF"),
            ProductItem(R.drawable.garlic, "Garlic", "11 MINS", "100 g", emptyList(), 0, "₹39", "₹49", "20% OFF")
        )
    }
}
