package com.example.blinkit_clone.data.model

data class ProductItem(
    val imageRes: Int,
    val name: String,
    val deliveryTime: String,
    val quantity: String,
    val tags: List<String>,
    val recipeCount: Int,
    // ✅ THE FIX: Changed price and mrp to Double for calculations
    val price: Double,
    val mrp: Double,
    val discountPercentage: String
)
