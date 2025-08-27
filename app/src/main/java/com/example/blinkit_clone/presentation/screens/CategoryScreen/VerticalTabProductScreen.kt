package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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



// --- Data Class ---
data class Category(
    val name: String,
    val iconRes: Int
)

// --- UI Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalTabProductsScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel(),
    viewModel: VerticalTabViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val productItems by viewModel.productItems.collectAsState()
    val filters by viewModel.filters.collectAsState()

    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(categories) {
        if (selectedCategory == null && categories.isNotEmpty()) {
            selectedCategory = categories.first()
        }
    }

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
            if (categories.isNotEmpty()) {
                CategorySidebar(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }
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
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .width(80.dp)
            .fillMaxHeight()
            .background(Color.White)
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
            .background(if (isSelected) Color.Gray.copy(alpha = 0.1f) else Color.Transparent)
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
                        color = MaterialTheme.colorScheme.primary,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterAndSortRow(filters: List<String>) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    LazyRow(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            FilterChip(
                selected = filter == selectedFilter,
                onClick = { selectedFilter = if (selectedFilter == filter) null else filter },
                label = { Text(filter) }
                // ✅ THE FIX: The problematic 'border' parameter has been removed.
                // The chip will now use its default border, which avoids the error.
            )
        }
    }
}
