package com.example.blinkit_clone.presentation.screens.CategoryScreens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import com.example.blinkit_clone.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.Utills.OrderAgainViewModel
import com.example.blinkit_clone.data.model.ProductItem
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BlinkitSearchBar
import com.example.blinkit_clone.presentation.screens.CategoryScreen.ProductCard


@Composable
fun OrderAgainScreen(
    navController: NavHostController,
    listState: LazyListState,
    // ✅ THE FIX: Made the cartViewModel optional by providing a default hiltViewModel().
    cartViewModel: CartViewModel = hiltViewModel(),
    orderAgainViewModel: OrderAgainViewModel = hiltViewModel()
) {
    val firstVisibleItemScrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val isScrolled by remember { derivedStateOf { firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0 } }
    val productItems by orderAgainViewModel.productItems.collectAsState()

    val headerHeightdp by animateDpAsState(
        targetValue = if (isScrolled) 0.dp else 88.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "HeaderHeight"
    )

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
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
                    .padding(horizontal = 4.dp)
                    .shadow(elevation = 8.dp)
            ) {
                Column(modifier = Modifier.height(headerHeightdp)) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Grocery in,", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black.copy(alpha = 0.7f))
                            Text("10 minutes", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color = Color.Black.copy(alpha = 0.5f))
                                .clickable { /* Navigate to profile screen */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                    ) {
                        Text("Bishapura, Vijay Nagar, Bhim Nagar, Vijay", fontSize = 14.sp, color = Color.Black.copy(alpha = 0.8f), maxLines = 1)
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Expand", tint = Color.Black)
                    }
                }
                BlinkitSearchBar(navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize().padding(innerPadding),
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

            items(productItems) { product ->
                ProductCard(
                    product = product,
                    navController = navController,
                    cartViewModel = cartViewModel
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Made in India",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray.copy(alpha = 0.8f)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "last Minute app",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(R.drawable.heart1),
                            contentDescription = "heart",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Gray.copy(alpha = 0.8f)
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = colorResource(R.color.lightGray),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrderAgainScreenPreview() {
    val navController = rememberNavController()
    val listState = rememberLazyListState()
    OrderAgainScreen(
        navController = navController,
        listState = listState,
        cartViewModel = hiltViewModel() // Use hiltViewModel for preview
    )
}

