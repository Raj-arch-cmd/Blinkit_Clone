package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.data.model.ProductItem
import com.example.blinkit_clone.presentation.components.QuantitySelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()

    // Example product - in a real app, this would be passed as an argument
    val product = remember {
        ProductItem(
            imageRes = R.drawable.springroll,
            name = "Hydroponic Sweet Bell Pepper",
            deliveryTime = "10 MINS",
            quantity = "2 pcs (200-350 g)",
            tags = emptyList(),
            recipeCount = 0,
            price = 170.0,
            mrp = 250.0,
            discountPercentage = "32% OFF"
        )
    }

    val quantityInCart by cartViewModel.cartItems.collectAsState()
    val currentQuantity = quantityInCart[product] ?: 0

    val showTitle by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0 ||
                    lazyListState.firstVisibleItemScrollOffset > 150
        }
    }

    val topBarAlpha by animateFloatAsState(
        targetValue = if (showTitle) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "topBarAlpha"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = product.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                modifier = Modifier.alpha(topBarAlpha),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .background(Color.White, shape = CircleShape)
                            .size(34.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrowdown),
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White.copy(alpha = topBarAlpha),
                    scrolledContainerColor = Color.White
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row {
                        Text(text = String.format("₹%.2f", product.price), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = String.format("₹%.2f", product.mrp),
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray
                        )
                    }
                    Text(text = "Inclusive of all taxes", fontSize = 12.sp, color = Color.Gray)
                }

                if (currentQuantity == 0) {
                    Button(
                        onClick = { cartViewModel.addProduct(product) },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.viewActivityClickbleColor)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text(text = "Add to cart", fontSize = 16.sp)
                    }
                } else {
                    QuantitySelector(
                        quantity = currentQuantity,
                        onAdd = { cartViewModel.addProduct(product) },
                        onRemove = { cartViewModel.removeProduct(product) },
                        modifier = Modifier.width(130.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(color = Color.LightGray.copy(alpha = 0.1f))
        ) {
            item {
                Image(
                    painter = painterResource(product.imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ProductDetailCard(product = product)
                    PeopleAlsoBoughtCard(navController, cartViewModel = cartViewModel)
                }
            }
        }
    }
}

@Composable
fun ProductDetailCard(product: ProductItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(product.name, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(product.quantity, fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(String.format("₹%.2f", product.price), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(String.format("MRP ₹%.2f", product.mrp), fontSize = 16.sp, color = Color.Gray, textDecoration = TextDecoration.LineThrough)
                Spacer(modifier = Modifier.width(12.dp))
                if (product.discountPercentage.isNotEmpty()) {
                    Box(
                        modifier = Modifier.background(Color.Blue.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp))
                    ) {
                        Text(
                            text = product.discountPercentage,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Blue
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.timer),
                    tint = colorResource(R.color.green),
                    modifier = Modifier.size(20.dp),
                    contentDescription = "timer"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(product.deliveryTime, fontSize = 14.sp, color = Color.DarkGray, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun PeopleAlsoBoughtCard(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val productItems = remember {
        listOf(
            ProductItem(R.drawable.milk, "Pooja Flower Mix", "11 MINS", "100 g", emptyList(), 0, 39.0, 49.0, "20% OFF"),
            ProductItem(R.drawable.milk, "Banana", "11 MINS", "3 pieces", listOf("Energy Booster"), 19, 39.0, 51.0, "23% OFF")
        )
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "People also bought",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp) // Adjust height as needed
            ) {
                items(productItems) { product ->
                    ProductCard(
                        product = product,
                        navController = navController,
                        cartViewModel = cartViewModel
                    )
                }
            }
        }
    }
}
