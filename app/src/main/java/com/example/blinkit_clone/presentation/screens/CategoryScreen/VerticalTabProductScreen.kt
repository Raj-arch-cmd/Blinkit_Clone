package com.example.blinkit_clone.presentation.screens.CategoryScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.R
import com.example.blinkit_clone.data.model.ProductItem


// Data Class used in this screen
data class Category(
    val name: String,
    val iconRes: Int
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalTabProductsScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
) {

    val categories = listOf(
        Category(name = "All", R.drawable.milk),
        Category(name = "Fresh Vegetables", R.drawable.milk),
        Category(name = "Fresh Fruits", R.drawable.milk),
        Category(name = "Exotics", R.drawable.milk),
        Category(name = "Coriander & Others", R.drawable.milk),
        Category(name = "Flowers & Leaves", R.drawable.milk),
        Category(name = "Seasonal", R.drawable.milk),
        Category(name = "Freshly Cut & Sprouts", R.drawable.milk)
    )

    // ✅ THE FIX: Updated prices from String to Double
    val productItems = listOf(
        ProductItem(R.drawable.milk, "Pooja Flower Mix", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
        ProductItem(R.drawable.milk, "Banana", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "23% OFF"),
        ProductItem(R.drawable.milk, "Cold Pressed Aam Panna Juice", "11 MINS", "200 ml", emptyList(), 0, 51.0, 63.0, "19% OFF"),
        ProductItem(R.drawable.milk, "Potato - New Crop (Aloo)", "11 MINS", "0.95 - 1.05 kg", emptyList(), 30, 29.0, 37.0, "21% OFF"),
        ProductItem(R.drawable.milk, "Broccoli", "11 MINS", "100 g - 400 g", emptyList(), 0, 49.0, 56.0, ""),
        ProductItem(R.drawable.milk, "Sweet Corn - Packet", "11 MINS", "180 g - 200 g", listOf("High Iron"), 0, 19.0, 47.0, "")
    )

    val filters = listOf("Filter", "Tomato", "Apple", "Kiwi", "Vegetables")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Vegetables & Fruits") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Search action */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        }
    ) { innerPadding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CategorySidebar(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                FilterAndSortRow(filters = filters)
                ProductGrid(
                    products = productItems,
                    navController = navController,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}


@Composable
fun CategorySidebar(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .width(80.dp)
            .fillMaxHeight()
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color = Color(0xFFFDF0F0)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = category.iconRes),
                    contentDescription = category.name,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.name,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = Color.DarkGray,
                maxLines = 2,
                lineHeight = 14.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(90.dp)
                    .background(
                        color = Color(0xFFF28942B),
                        shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                    )
            )
        } else {
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun ProductGrid(
    products: List<ProductItem>,
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
    }
}

@Composable
fun FilterAndSortRow(filters: List<String>) {
    // Placeholder for your filter UI
}
