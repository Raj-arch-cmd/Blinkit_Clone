package com.example.blinkit_clone.Utills

import androidx.lifecycle.ViewModel
import com.example.blinkit_clone.data.model.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _cartItems = MutableStateFlow<Map<ProductItem, Int>>(emptyMap())
    val cartItems = _cartItems.asStateFlow()

    val totalItemCount = cartItems.map { it.values.sum() }
    val totalPrice = cartItems.map { cart ->
        cart.entries.sumOf { (product, quantity) -> product.price * quantity }
    }

    fun addProduct(product: ProductItem) {
        _cartItems.update { currentCart ->
            val newCart = currentCart.toMutableMap()
            val currentQuantity = newCart.getOrDefault(product, 0)
            newCart[product] = currentQuantity + 1
            newCart
        }
    }

    fun removeProduct(product: ProductItem) {
        _cartItems.update { currentCart ->
            val newCart = currentCart.toMutableMap()
            val currentQuantity = newCart.getOrDefault(product, 0)
            if (currentQuantity > 1) {
                newCart[product] = currentQuantity - 1
            } else {
                newCart.remove(product)
            }
            newCart
        }
    }

    fun getQuantity(product: ProductItem): Int {
        return _cartItems.value.getOrDefault(product, 0)
    }

    // ✅ THE FIX: Added the missing clearCart function.
    fun clearCart() {
        _cartItems.value = emptyMap()
    }
}

