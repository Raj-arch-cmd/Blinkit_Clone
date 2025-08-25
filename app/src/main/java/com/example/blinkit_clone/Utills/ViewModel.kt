package com.example.blinkit_clone.Utills

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _cartItemCount = mutableStateOf(0)
    val cartItemCount: State<Int> = _cartItemCount

    private val _showCartButton = mutableStateOf(false)
    val showCartButton: State<Boolean> = _showCartButton

    fun addItem() {
        _cartItemCount.value += 1
        _showCartButton.value = true
    }

    fun decreaseItem() {
        _cartItemCount.value--
        if (_cartItemCount.value == 0) {
            _showCartButton.value = false
        }
    }

    fun clearCart() {
        _cartItemCount.value = 0
        _showCartButton.value = false
    }

    fun dismissCartButton() {
        _showCartButton.value = false
    }
}