package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.data.model.ProductItem
import com.example.blinkit_clone.presentation.components.QuantitySelector
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalCheckOutScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState(initial = 0.0)
    var showOrderDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Checkout",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.arrowback_24),
                            modifier = Modifier.size(22.dp),
                            contentDescription = "Back Navigation"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    showOrderDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.greendivider)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Place Order",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.LightGray.copy(alpha = 0.1f)),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                DeliveryDetailsCard(cartItems.size)
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(cartItems.keys.toList()) { product ->
                CheckoutItemRow(
                    product = product,
                    quantity = cartItems[product] ?: 0,
                    cartViewModel = cartViewModel
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                BillDetailsCard(totalPrice)
                Spacer(modifier = Modifier.height(16.dp))
                CancellationPolicy()
            }
        }
        if (showOrderDialog) {
            OrderPlacedDialog(
                onDismiss = { showOrderDialog = false },
                onConfirm = {
                    showOrderDialog = false
                    cartViewModel.clearCart()
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun DeliveryDetailsCard(itemCount: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.timer),
                modifier = Modifier.size(22.dp),
                tint = colorResource(R.color.green),
                contentDescription = "time"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = "Free delivery in 11 minutes", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "Shipment of $itemCount item${if (itemCount > 1) "s" else ""}", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun CheckoutItemRow(
    product: ProductItem,
    quantity: Int,
    cartViewModel: CartViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.imageRes,
            contentDescription = "Product",
            modifier = Modifier
                .size(60.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(product.quantity, fontSize = 12.sp, lineHeight = 16.sp, color = Color.DarkGray)
            Text(String.format("₹%.2f", product.price), fontWeight = FontWeight.SemiBold)
        }
        QuantitySelector(
            quantity = quantity,
            onAdd = { cartViewModel.addProduct(product) },
            onRemove = { cartViewModel.removeProduct(product) },
            modifier = Modifier.width(110.dp)
        )
    }
}

@Composable
fun BillDetailsCard(totalPrice: Double) {
    val deliveryCharge = 15.0
    val handlingCharge = 2.0
    val grandTotal = totalPrice + deliveryCharge + handlingCharge

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            Text(text = "Bill details", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            BillRow(icon = R.drawable.notes, label = "Items total", amount = String.format("₹%.2f", totalPrice))
            BillRow(icon = R.drawable.delivery, label = "Delivery Charge", amount = String.format("₹%.2f", deliveryCharge))
            BillRow(icon = R.drawable.shopping_bag, label = "Handling Charge", amount = String.format("₹%.2f", handlingCharge))

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray, thickness = 0.5.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Grand Total", modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = String.format("₹%.2f", grandTotal), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun BillRow(icon: Int, label: String, amount: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            modifier = Modifier.size(14.dp),
            contentDescription = label,
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = label, color = Color.DarkGray, fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = amount, color = Color.DarkGray, fontSize = 14.sp)
    }
}

@Composable
fun CancellationPolicy() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "CANCELLATION POLICY", color = Color.DarkGray, fontWeight = FontWeight.Bold)
        Text(
            text = "Help us reduce food waste by avoiding cancellations. The amount paid is non-refundable after placing the order",
            fontSize = 12.sp,
            lineHeight = 14.sp,
            color = Color.Gray
        )
    }
}

