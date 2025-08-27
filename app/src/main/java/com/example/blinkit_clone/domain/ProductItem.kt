package com.example.blinkit_clone.domain

data class ProductItem(
    val imageRes: Int,
    val name: String,
    val deliveryTime: String,
    val quantity: String,
    val tags: List<String>,
    val recipeCount: Int,
    val price: String,
    val mrp: String,
    val discountPercentage: String
)
