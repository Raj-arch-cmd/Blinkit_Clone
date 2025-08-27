package com.example.projectnew.presentation.screens.CategoryScreens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
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
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BlinkitSearchBar
import com.example.blinkit_clone.presentation.screens.CategoryScreen.ProductCard

@Composable
fun OrderAgainScreen(
    navController: NavHostController,
    listState: LazyListState,
    cartViewModel: CartViewModel = hiltViewModel(),
    viewModel: OrderAgainViewModel = hiltViewModel()
) {
    val firstVisibleItemScrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val isScrolled by remember { derivedStateOf { firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0 } }

    val headerHeightdp by animateDpAsState(
        targetValue = if (isScrolled) 0.dp else 88.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "HeaderHeight"
    )

    val productItems by viewModel.productItems.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF9F9F9),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF6F6A1E),
                                Color(0xFF9FAD4C)
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
                            Text("Grocery in,", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White.copy(alpha = 0.8f))
                            Text("10 minutes", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color = Color.White.copy(alpha = 0.2f))
                                .clickable { /* Nav to profile */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                    ) {
                        Text("Bishapura, Vijay Nagar...", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f), maxLines = 1)
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Expand", tint = Color.White)
                    }
                }
                BlinkitSearchBar(navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    ) { innerPadding ->
        // ✅ THE FIX: Replaced LazyVerticalGrid with a LazyColumn.
        // This allows the screen to correctly use the listState for animations.
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    text = "Bestsellers",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // ✅ THE FIX: Manually create a grid layout using chunked rows.
            items(productItems.chunked(3)) { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { product ->
                        Box(modifier = Modifier.weight(1f)) {
                            ProductCard(
                                product = product,
                                navController = navController,
                                cartViewModel = cartViewModel
                            )
                        }
                    }
                    // Add spacers if the row is not full to maintain alignment
                    if (rowItems.size < 3) {
                        for (i in 0 until (3 - rowItems.size)) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.blinkit_logo),
                        contentDescription = "Blinkit Logo",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Gray.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Made in India with ❤️",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        color = Color.LightGray,
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
    OrderAgainScreen(navController = navController, listState = listState)
}
