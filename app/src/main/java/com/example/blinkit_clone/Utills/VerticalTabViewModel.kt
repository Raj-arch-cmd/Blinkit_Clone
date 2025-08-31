package com.example.blinkit_clone.Utills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blinkit_clone.R
import com.example.blinkit_clone.data.model.ProductItem
import com.example.blinkit_clone.presentation.screens.CategoryScreen.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerticalTabViewModel @Inject constructor() : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _productItems = MutableStateFlow<List<ProductItem>>(emptyList())
    val productItems = _productItems.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _categories.value = listOf(
                Category(name = "All", R.drawable.milk),
                Category(name = "Fresh Vegetables", R.drawable.milk),
                Category(name = "Fresh Fruits", R.drawable.milk),
                Category(name = "Exotics", R.drawable.milk),
                Category(name = "Coriander & Others", R.drawable.milk),
                Category(name = "Flowers & Leaves", R.drawable.milk),
                Category(name = "Seasonal", R.drawable.milk),
                Category(name = "Freshly Cut & Sprouts", R.drawable.milk)
            )

            // ✅ THE FIX: Prices and MRP are now Doubles (numbers), not Strings (text).
            _productItems.value = listOf(
                ProductItem(R.drawable.milk, "Pooja Flower Mix", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
                ProductItem(R.drawable.milk, "Banana", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "23% OFF"),
                ProductItem(R.drawable.milk, "Cold Pressed Aam Panna Juice", "11 MINS", "200 ml", emptyList(), 0, 51.0, 63.0, "19% OFF"),
                ProductItem(R.drawable.milk, "Potato - New Crop (Aloo)", "11 MINS", "0.95 - 1.05 kg", emptyList(), 30, 29.0, 37.0, "21% OFF"),
                ProductItem(R.drawable.milk, "Broccoli", "11 MINS", "100 g - 400 g", emptyList(), 0, 49.0, 56.0, "12% OFF"),
                ProductItem(R.drawable.milk, "Sweet Corn - Packet", "11 MINS", "180 g - 200 g", listOf("High Iron"), 0, 19.0, 47.0, "59% OFF")
            )
        }
    }
}
