package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.Utills.SearchViewModel
import com.example.blinkit_clone.presentation.screens.CategoryScreen.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    // Inject the new SearchViewModel
    viewModel: SearchViewModel = hiltViewModel()
) {
    // Collect state from the ViewModel
    val searchText by viewModel.searchText.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()

    val recentSearches = remember { listOf("Milk", "Bread", "Eggs", "Fruits") }
    val popularSearches = remember {
        listOf("Maggie", "Dairy Products", "Snacks", "Tea", "Chocolate")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchText,
                        onValueChange = viewModel::onSearchTextChange,
                        placeholder = { Text("Search for products...") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (searchText.isEmpty()) {
                // Show recent and popular searches when the search bar is empty
                RecentAndPopularSearches(
                    recentSearches = recentSearches,
                    popularSearches = popularSearches,
                    onSearchClicked = viewModel::onSearchTextChange
                )
            } else {
                // Show search results when the user is typing
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(searchResults) { product ->
                        val quantity = cartItems[product] ?: 0
                        // Use the reusable ProductCard to display results
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
    }
}

@Composable
private fun RecentAndPopularSearches(
    recentSearches: List<String>,
    popularSearches: List<String>,
    onSearchClicked: (String) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        item {
            Text("Recent Searches", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
        }
        items(recentSearches) { search ->
            SearchHistoryItem(search) { onSearchClicked(search) }
        }
        item {
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text("Popular Searches", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
        }
        items(popularSearches) { category ->
            SearchCategoryItem(category) { onSearchClicked(category) }
        }
    }
}


@Composable
fun SearchHistoryItem(query: String, onClick: () -> Unit) {
    Text(
        text = query,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        color = Color.Gray
    )
}

@Composable
fun SearchCategoryItem(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.2f))
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(16.dp),
            color = Color.DarkGray
        )
    }
}


