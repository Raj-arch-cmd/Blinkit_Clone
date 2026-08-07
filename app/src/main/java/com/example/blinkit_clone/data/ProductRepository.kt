package com.example.blinkit_clone.data

import com.example.blinkit_clone.R
import com.example.blinkit_clone.data.model.ProductItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor() {

    // ✅ THE FIX: Created a master list of all products for searching.
    private val allProducts = listOf(
        // Products from Order Again Screen
        ProductItem(R.drawable.kitchen, "Kitchen Appliances", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
        ProductItem(R.drawable.began, "Began", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "23% OFF"),
        ProductItem(R.drawable.cabbage, "Cabbage", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
        ProductItem(R.drawable.carrot, "Carrot", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "23% OFF"),
        ProductItem(R.drawable.capcicum, "Capsicum", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "23% OFF"),
        ProductItem(R.drawable.garlic, "Garlic", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
        ProductItem(R.drawable.choclate, "Chocolate", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "20% OFF"),
        ProductItem(R.drawable.dryfruits, "Dry fruits", "11 MINS", "100 g", emptyList(), 0, 349.0, 449.0, "20% OFF"),
        ProductItem(R.drawable.milk, "Milk", "11 MINS", "100 g", emptyList(), 0, 39.0, 59.0, "20% OFF"),
        ProductItem(R.drawable.instantfood, "Maggie", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
        ProductItem(R.drawable.tea, "Tea", "11 MINS", "100 gm", listOf("Energy Booster"), 19, 129.0, 151.0, "23% OFF"),
        ProductItem(R.drawable.fruitsandvegetables, "Fruit Basket", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 59.0, 51.0, "23% OFF"),
        // Products from Favorites Screen
        ProductItem(R.drawable.milk, "Mother Dairy Classic Curd", "10 MINS", "400 g", emptyList(), 0, 36.0, 38.0, "5% OFF"),
        ProductItem(R.drawable.bread, "Whole Wheat Bread", "10 MINS", "400g", emptyList(), 0, 45.0, 48.0, "5% OFF"),
        ProductItem(R.drawable.choclate, "Cadbury Dairy Milk", "12 MINS", "50 g", emptyList(), 0, 40.0, 44.0, "10% OFF")
        // You can add more products from other screens here...
    )

    fun getOrderAgainProducts(): List<ProductItem> {
        return allProducts.take(12) // Just return the first 12 for this screen
    }

    // ✅ THE FIX: Added a function to search the master list.
    fun searchProducts(query: String): List<ProductItem> {
        if (query.isBlank()) {
            return emptyList()
        }
        return allProducts.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}

