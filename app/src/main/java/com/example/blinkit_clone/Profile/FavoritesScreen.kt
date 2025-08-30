package com.example.blinkit_clone.Profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.presentation.screens.CategoryScreen.ProductCard
import com.example.blinkit_clone.presentation.screens.CategoryScreen.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {
    private val _favoriteItems = MutableStateFlow<List<ProductItem>>(emptyList())
    val favoriteItems = _favoriteItems.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        _favoriteItems.value = listOf(
            ProductItem(R.drawable.milk, "Mother Dairy Classic Curd", "10 MINS", "400 g", emptyList(), 0, "₹36", "₹38", "5% OFF"),
            ProductItem(R.drawable.bread, "Whole Wheat Bread", "10 MINS", "400g", emptyList(), 0, "₹45", "₹48", "5% OFF"),
            ProductItem(R.drawable.choclate, "Cadbury Dairy Milk", "12 MINS", "50 g", emptyList(), 0, "₹40", "₹44", "0% OFF")
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteItems by viewModel.favoriteItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favoriteItems) { product ->
                ProductCard(product = product, navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(navController = rememberNavController())
}
