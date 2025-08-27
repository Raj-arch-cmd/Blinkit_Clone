package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.lifecycle.ViewModel
import com.example.blinkit_clone.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// ✅ THE FIX: This ViewModel now has the correct package name to match your screen.
@HiltViewModel
class VerticalTabViewModel @Inject constructor() : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _productItems = MutableStateFlow<List<ProductItem>>(emptyList())
    val productItems = _productItems.asStateFlow()

    private val _filters = MutableStateFlow<List<String>>(emptyList())
    val filters = _filters.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
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
        _productItems.value = listOf(
            ProductItem(R.drawable.milk, "Pooja Flower Mix", "11 MINS", "100 g", emptyList(), 0, "₹39", "₹49", "20% OFF"),
            ProductItem(R.drawable.milk, "Banana", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, "₹39", "₹51", "23% OFF"),
            ProductItem(R.drawable.milk, "Cold Pressed Aam Panna Juice", "11 MINS", "200 ml", emptyList(), 0, "₹51", "₹63", "19% OFF"),
            ProductItem(R.drawable.milk, "Potato - New Crop (Aloo)", "11 MINS", "0.95 - 1.05 kg", emptyList(), 30, "₹29", "₹37", "21% OFF"),
            ProductItem(R.drawable.milk, "Broccoli", "11 MINS", "100 g - 400 g", emptyList(), 0, "₹49", "₹56", ""),
            ProductItem(R.drawable.milk, "Sweet Corn - Packet", "11 MINS", "180 g - 200 g", listOf("High Iron"), 0, "₹19", "₹47", "")
        )
        _filters.value = listOf("Filter", "Tomato", "Apple", "Kiwi", "Vegetables")
    }
}
