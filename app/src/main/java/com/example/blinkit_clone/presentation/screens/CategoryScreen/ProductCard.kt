package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.data.model.ProductItem
import com.example.blinkit_clone.presentation.components.QuantitySelector

@Composable
fun ProductCard(
    product: ProductItem,
    itemQuantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* Navigate to product detail if needed */ },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.4f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .height(90.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                minLines = 2 // Ensures consistent height
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.defaultMinSize(minHeight = 48.dp) // Ensures this row has space even if prices wrap
            ) {
                Text(
                    text = String.format("₹%.2f", product.price),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = String.format("₹%.2f", product.mrp),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp), // Set a fixed height for the container
                contentAlignment = Alignment.Center
            ) {
                if (itemQuantity == 0) {
                    Button(
                        onClick = onAdd,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF8E479).copy(alpha = 0.5f)),
                        border = BorderStroke(1.dp, Color(0xFF0E8A44))
                    ) {
                        Text("ADD", color = Color(0xFF0E8A44), fontWeight = FontWeight.Bold)
                    }
                } else {
                    QuantitySelector(
                        quantity = itemQuantity,
                        onAdd = onAdd,
                        onRemove = onRemove,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    val navController = rememberNavController()
    val productInCart = ProductItem(
        imageRes = R.drawable.tea,
        name = "Red Label Tea",
        deliveryTime = "10 MINS",
        quantity = "500 g",
        tags = emptyList(),
        recipeCount = 0,
        price = 129.00,
        mrp = 151.00,
        discountPercentage = "15% OFF"
    )
    val productNotInCart = ProductItem(
        imageRes = R.drawable.milk,
        name = "Mother Dairy Classic Curd",
        deliveryTime = "10 MINS",
        quantity = "400 g",
        tags = emptyList(),
        recipeCount = 0,
        price = 36.0,
        mrp = 38.0,
        discountPercentage = "5% OFF"
    )

    Row(

        Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            ProductCard(
                product = productNotInCart,
                itemQuantity = 0,
                onAdd = {},
                onRemove = {},
                navController = navController
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            ProductCard(
                product = productInCart,
                itemQuantity = 1,
                onAdd = {},
                onRemove = {},
                navController = navController
            )
        }
    }
}

