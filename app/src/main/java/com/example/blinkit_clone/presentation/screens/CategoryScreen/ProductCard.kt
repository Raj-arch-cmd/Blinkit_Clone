package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
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
        border = BorderStroke(0.5.dp, Color.LightGray.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AsyncImage(
                model = product.imageRes,
                contentDescription = product.name,
                modifier = Modifier
                    .height(90.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                minLines = 2,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.defaultMinSize(minHeight = 40.dp)
            ) {
                Text(
                    text = String.format("₹%.0f", product.price),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = String.format("₹%.0f", product.mrp),
                    fontSize = 11.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                contentAlignment = Alignment.Center
            ) {
                if (itemQuantity == 0) {
                    Button(
                        onClick = onAdd,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF0E8A44)
                        ),
                        border = BorderStroke(1.dp, Color(0xFF0E8A44).copy(alpha = 0.7f)),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("ADD", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
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

