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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()
    val productName by remember { mutableStateOf("Hydroponic Sweet Bell Pepper") }
    val quantity by remember { mutableStateOf("2 pcs (200-350 g)") }
    val price by remember { mutableStateOf("₹170") }
    val mrp by remember { mutableStateOf("₹250") }
    val offer by remember { mutableStateOf("32% OFF") }
    val deliveryTime by remember { mutableStateOf("10 MINS") }

    val showTitle by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0 ||
                    lazyListState.firstVisibleItemScrollOffset > 150
        }
    }

    val topBarAlpha by animateFloatAsState(
        targetValue = if (showTitle) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hydroponic Sweet Bell Pepper",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                modifier = Modifier.alpha(topBarAlpha),
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .background(Color.White, shape = CircleShape)
                            .size(34.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .background(Color.White, shape = CircleShape)
                            .size(34.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.faltuh),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
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
                            contentDescription = null,
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
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Column {
                    Text(text = quantity, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Row {
                        Text(text = price, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = mrp,
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    Text(text = "Inclusive all taxes", fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { cartViewModel.addItem() },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.viewActivityClickbleColor)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Add to cart", fontSize = 16.sp)
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(color = colorResource(R.color.gray))
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(R.color.gray))
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(R.drawable.springroll),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        ProductDetailCard(
                            productName = productName,
                            quantity = quantity,
                            price = price,
                            mrp = mrp,
                            offer = offer,
                            deliveryTime = deliveryTime
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        PeopleAlsoBoughtCard(navController, cartViewModel = cartViewModel)
                    }
                }
            }
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 10.dp)) {
                    // This section appears to be a footer
                    Text(text = "Minute app", fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Icon(
                        painter = painterResource(R.drawable.heart1),
                        contentDescription = "Heart",
                        modifier = Modifier.padding(top = 10.dp).size(60.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        color = colorResource(R.color.gray),
                        thickness = 1.5.dp
                    )
                    Text(
                        text = "Grocers",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
fun ProductDetailCard(
    productName: String,
    quantity: String,
    price: String,
    mrp: String,
    offer: String,
    deliveryTime: String
) {
    Card(
        modifier = Modifier.width(92.dp), // Note: width seems small, transcribing as is
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
            bottomStart = 8.dp,
            bottomEnd = 8.dp
        ),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = productName,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = quantity,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "MRP", // Note: This appears redundant with the next line, transcribing as is
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = mrp,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Blue.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        )
                ) {
                    Text(
                        text = offer,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(4.dp),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Blue
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.timer),
                    tint = colorResource(R.color.green),
                    modifier = Modifier.size(14.dp),
                    contentDescription = "timer"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = deliveryTime,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View Product details",
                    fontSize = 16.sp,
                    color = colorResource(R.color.viewActivityClickbleColor),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(R.drawable.arrowdown),
                    modifier = Modifier.size(10.dp),
                    tint = colorResource(R.color.viewActivityClickbleColor),
                    contentDescription = "detail arrow"
                )
            }
        }
    }
}

@Composable
fun PeopleAlsoBoughtCard(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    // Note: The source images showed many duplicate items.
    // This list represents the unique items shown.

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "People also bought",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(12.dp)
            )
            LazyVerticalGrid(
                userScrollEnabled = false,
                columns = GridCells.Fixed(3), // 3 items in one row
                contentPadding = PaddingValues(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1010.dp) // Note: This is a very large fixed height
            ) {

            }
        }
    }
}
