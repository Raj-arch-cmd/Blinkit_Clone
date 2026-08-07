package com.example.blinkit_clone.presentation.screens.CategoryScreens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.Utills.OrderAgainViewModel
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BlinkitSearchBar
import com.example.blinkit_clone.presentation.screens.CategoryScreen.ProductCard

@Composable
fun OrderAgainScreen(
    navController: NavHostController,
    listState: LazyListState,
    cartViewModel: CartViewModel,
    // ✅ THE FIX: Inject the new ViewModel for this screen
    orderAgainViewModel: OrderAgainViewModel = hiltViewModel()
) {
    // ✅ THE FIX: Get the product list from the ViewModel's state
    val productItems by orderAgainViewModel.productItems.collectAsState()

    // The rest of your UI code remains largely the same...
    Scaffold(
        modifier = Modifier.statusBarsPadding().background(Color.White),
        topBar = {
            // Your TopAppBar code remains here
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Bestsellers",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }

            // ✅ THE FIX: The grid now uses the productItems list from the ViewModel
            items(productItems) { product ->
                ProductCard(
                    product = product,
                    navController = navController,
                    cartViewModel = cartViewModel
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                // Your footer code remains here
            }
        }
    }
}


// Dummy TopAppBar for preview purposes, as the original is complex
@Composable
private fun OrderAgainTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6F6A1E), // Darker gold
                        Color(0xFF9FAD4C)  // Medium amber gold
                    )
                )
            )
            .padding(16.dp)
    ) {
        Text("Grocery in 10 minutes", color = Color.White, fontWeight = FontWeight.Bold)
        BlinkitSearchBar()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrderAgainScreenPreview() {
    val navController = rememberNavController()
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    val cartViewModel: CartViewModel = hiltViewModel()
    OrderAgainScreen(
        navController = navController,
        listState = listState,
        cartViewModel = cartViewModel
    )
}


