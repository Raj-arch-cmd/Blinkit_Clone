package com.example.blinkit_clone.Utills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blinkit_clone.R
import com.example.blinkit_clone.data.model.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderAgainViewModel @Inject constructor() : ViewModel() {

    private val _productItems = MutableStateFlow<List<ProductItem>>(emptyList())
    val productItems = _productItems.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            // ✅ THE FIX: Prices and MRP are now Doubles (numbers), not Strings (text).
            _productItems.value = listOf(
                ProductItem(R.drawable.kitchen, "Kitchen Appliances", "11 MINS", "100 g", listOf("Fiber Rich"), 0, 39.0, 49.0, "20% OFF"),
                ProductItem(R.drawable.began, "Began", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "23% OFF"),
                ProductItem(R.drawable.cabbage, "Cabbage", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
                ProductItem(R.drawable.carrot, "Carrot", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "23% OFF"),
                ProductItem(R.drawable.capcicum, "Capsicum", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "23% OFF"),
                ProductItem(R.drawable.garlic, "Garlic", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
                ProductItem(R.drawable.choclate, "Chocolate", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "20% OFF"),
                ProductItem(R.drawable.dryfruits, "Dry fruits", "11 MINS", "100 g", emptyList(), 0, 349.0, 449.0, "22% OFF"),
                ProductItem(R.drawable.milk, "Milk", "11 MINS", "100 g", emptyList(), 0, 39.0, 59.0, "33% OFF"),
                ProductItem(R.drawable.instantfood, "Maggie", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
                ProductItem(R.drawable.tea, "Tea", "11 MINS", "100 gm", listOf("Energy Booster"), 19, 129.0, 151.0, "14% OFF"),
                ProductItem(R.drawable.fruitsandvegetables, "Fruit Basket", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 59.0, 51.0, "23% OFF")
            )
        }
    }
}
