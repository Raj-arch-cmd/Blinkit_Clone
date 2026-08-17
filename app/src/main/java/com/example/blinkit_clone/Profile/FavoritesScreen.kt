package com.example.blinkit_clone.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.Profile.FavoritesViewModel
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.presentation.screens.CategoryScreen.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    // Accepts the shared CartViewModel from the MainScreen
    cartViewModel: CartViewModel,
    // Injects the new ViewModel to get the list of favorite products
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    // Collects the list of products as state from the ViewModel
    val favoriteProducts by favoritesViewModel.favoriteProducts.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Favorites") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favoriteProducts) { product ->
                val quantity = cartItems[product] ?: 0
                // Uses the standard ProductCard and passes the shared CartViewModel
                ProductCard(
                    product = product,
                    itemQuantity = quantity,
                    onAdd = { cartViewModel.addProduct(product) },
                    onRemove = { cartViewModel.removeProduct(product) },
                    navController = navController
                )
            }
        }
    }
}

