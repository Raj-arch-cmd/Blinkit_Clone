package com.example.blinkit_clone.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.data.model.ProductItem
import com.example.blinkit_clone.presentation.screens.CategoryScreen.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    // ✅ THE FIX: Prices and MRP are now Doubles (numbers), not Strings (text).
    val favoriteProducts = listOf(
        ProductItem(R.drawable.milk, "Mother Dairy Classic Curd", "10 MINS", "400 g", emptyList(), 0, 36.0, 38.0, "5% OFF"),
        ProductItem(R.drawable.bread, "Whole Wheat Bread", "10 MINS", "400g", emptyList(), 0, 45.0, 48.0, "5% OFF"),
        ProductItem(R.drawable.choclate, "Cadbury Dairy Milk", "12 MINS", "50 g", emptyList(), 0, 40.0, 44.0, "9% OFF"),
        ProductItem(R.drawable.tea, "Tata Tea Gold", "11 MINS", "250 g", emptyList(), 0, 150.0, 165.0, "9% OFF"),
        ProductItem(R.drawable.instantfood, "Maggi 2-Minute Noodles", "10 MINS", "70 g", emptyList(), 0, 12.0, 14.0, "14% OFF")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Favorites", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favoriteProducts) { product ->
                ProductCard(
                    product = product,
                    navController = navController,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}
