package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.data.model.ProductItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(navController: NavHostController, listState: LazyListState) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val recentSearches = remember { listOf("Milk", "Bread", "Eggs", "Fruits") }
    val popularSearches = remember {
        listOf(
            "Organic Products",
            "Dairy Products",
            "Snacks",
            "Beverages",
            "Personal Care"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Search for products...") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
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
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            if (searchText.text.isEmpty()) {
                // Recent Searches
                Text(
                    "Recent Searches",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                recentSearches.forEach { search ->
                    SearchHistoryItem(search) {
                        searchText = TextFieldValue(search)
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                // Popular Searches
                Text(
                    "Popular Categories",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                popularSearches.forEach { category ->
                    SearchCategoryItem(category) {
                        searchText = TextFieldValue(category)
                    }
                }
            } else {
                // Search Results
                val filteredResults = remember(searchText.text) {
                    // This would typically come from a ViewModel
                    listOf(
                        ProductItem(R.drawable.milk, "Organic Milk", "10 MINS", "1L", emptyList(), 0, 60.0, 67.0, "10% OFF"),
                        ProductItem(R.drawable.bread, "Whole Wheat Bread", "10 MINS", "400g", emptyList(), 0, 45.0, 48.0, "5% OFF")
                    ).filter { it.name.contains(searchText.text, ignoreCase = true) }
                }

                LazyColumn(state = listState) {
                    items(filteredResults) { product ->
                        SearchResultItem(product) {
                            navController.navigate(Screens.ProductScreen.route)
                        }
                    }
                }
            }
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
            .padding(16.dp),
        color = Color.Gray
    )
}

@Composable
fun SearchCategoryItem(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
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

@Composable
fun SearchResultItem(product: ProductItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(product.name, style = MaterialTheme.typography.bodyLarge)
            Text(product.quantity, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Text(String.format("₹%.2f", product.price), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
    Divider()
}

