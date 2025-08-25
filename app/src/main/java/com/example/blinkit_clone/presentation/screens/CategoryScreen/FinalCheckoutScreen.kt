package com.example.blinkit_clone.presentation.screens.CategoryScreen






import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun FinalCheckOutScreen(
    navController: NavHostController, cartViewModel: CartViewModel = hiltViewModel())
{
    var showOrderDialog by remember { mutableStateOf(false) }

    var time by remember { mutableStateOf("11") }
    var productName by remember { mutableStateOf("Pure tone milk") }
    var productQuantity by remember { mutableStateOf("1 (500ml)") }
    var price by remember { mutableStateOf("Rs.69") }
    var newPrice by remember { mutableStateOf("Rs.71") }
    var deliveryCharge by remember { mutableStateOf("25") }
    var handlingCharge by remember { mutableStateOf("2") }




    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Checkout",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.arrowback_24),
                            modifier = Modifier.size(22.dp),
                            contentDescription = "Back Navigation"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.faltuh),
                            modifier = Modifier.size(18.dp),
                            tint = Color.DarkGray,
                            contentDescription = "Back Navigation"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom).asPaddingValues())
                    .background(Color.White)
            ) {
                // Payment Button
                Button(
                    onClick = {
                        showOrderDialog = true
                        cartViewModel.clearCart()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.greendivider)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box( // Box ensures Text is centered while pushing the Icon to the end
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Text(
                            text = "Place Order",
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
            if (showOrderDialog) {
                OrderPlacedDialog(
                    onDismiss = { showOrderDialog = false },
                    onConfirm = {
                        showOrderDialog = false
                        // Add additional confirmation logic if needed
                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                ProductCardOfCheckout(
                    productName,
                    productQuantity,
                    price,
                    time,
                    cartViewModel
                )
                CouponCard()
                Spacer(modifier = Modifier.height(16.dp))
                BillCard(price, newPrice,deliveryCharge, handlingCharge)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.donationbanner),
                                contentDescription = "Restaurants near me",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "CANCELLATION POLICY",
                        modifier = Modifier.padding(horizontal = 4.dp),
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Help us reduce food waste by avoiding cancellations. The amount paid is non-refundable after placing the order",
                        modifier = Modifier.padding(horizontal = 4.dp),
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCardOfCheckout(
    productName: String,
    productQuantity: String,
    price: String,
    time: String,
    cartViewModel: CartViewModel
) {
    val itemCount by cartViewModel.cartItemCount

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.background(color = colorResource(R.color.gray),
                shape = RoundedCornerShape(6.dp)).padding(2.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    painter = painterResource(R.drawable.timer),
                    modifier = Modifier.size(22.dp),
                    tint = colorResource(R.color.green),
                    contentDescription = "time"
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column{
                Text(text = "Free delivery in $time minutes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)
                Text(text = "Shipment of $itemCount item",
                    fontSize = 14.sp,
                    color = Color.Gray)
            }

        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.LightGray,
            thickness = 0.6.dp)

        //Product Details
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            Box(modifier = Modifier.size(50.dp)
                .background(color = colorResource(R.color.gray),
                    shape = RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(R.drawable.milk),
                    contentDescription = "Product"
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = productName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = productQuantity,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "save for later",
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline,
                    color = Color.DarkGray
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            //Card to Handle Quantity
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Card(
                    modifier = Modifier.size(width = 75.dp, height = 28.dp),
                    colors = CardDefaults.cardColors(colorResource(R.color.green)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painterResource(R.drawable.milk),
                            modifier = Modifier
                                .padding(2.dp)
                                .clickable {
                                    cartViewModel.decreaseItem()
                                },
                            tint = Color.White,
                            contentDescription = null
                        )
                        Text(
                            text = itemCount.toString(),
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painterResource(R.drawable.milk),
                            modifier = Modifier
                                .padding(2.dp)
                                .size(18.dp)
                                .clickable {
                                    cartViewModel.addItem()
                                },
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }
                Text(
                    text = price,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
    }
}

// Coupons Section
@Composable
fun CouponCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.coupons),
                    contentDescription = "Coupons",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "View all coupons",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Icon(
                painter = painterResource(R.drawable.arrowright),
                contentDescription = "View",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun BillCard(
    price: String,
    newPrice: String,
    deliveryCharge: String,
    handlingCharge: String)
{
    // Delivery Information
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp)) {
            Text(text = "Bill details",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically)
            {
                Icon(
                    painter = painterResource(R.drawable.notes),
                    modifier = Modifier.size(14.dp),
                    contentDescription = "items"
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Items total",
                    color = Color.DarkGray,
                    fontSize = 14.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = price)
            }
            Spacer(modifier = Modifier.width(4.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically)
            {
                Icon(
                    painter = painterResource(R.drawable.delivery),
                    modifier = Modifier.size(16.dp),
                    contentDescription = "delivery"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Delivery Charge",
                    color = Color.DarkGray,
                    fontSize = 14.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = deliveryCharge, textDecoration = TextDecoration.LineThrough)
            }
            Spacer(modifier = Modifier.width(4.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically)
            {
                Icon(
                    painter = painterResource(R.drawable.shopping_bag),
                    modifier = Modifier.size(14.dp),
                    contentDescription = "handling"
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Handling Charge",
                    color = Color.DarkGray,
                    fontSize = 14.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = handlingCharge)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp),
                color = Color.LightGray,
                thickness = 0.5.dp)

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Grand Total",
                    modifier = Modifier.padding(4.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = newPrice, fontWeight = FontWeight.Bold)
            }
        }

    }
}

