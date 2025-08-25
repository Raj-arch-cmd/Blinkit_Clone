package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.Utills.CartViewModel

// ✅ THIS IS THE ONLY DEFINITION of ProductItem you should have in your project.
data class ProductItem(
    val imageRes: Int, // Must be Int for R.drawable resources
    val name: String,
    val deliveryTime: String,
    val quantity: String,
    val tags: List<String>, // Must be a List of Strings
    val recipeCount: Int,
    val price: String,
    val mrp: String,
    val discountPercentage: String
)

@Composable
fun ProductCard(
    product: ProductItem,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
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
                minLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.price,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = product.mrp,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { cartViewModel.addItem() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF8E479).copy(alpha = 0.5f)),
                border = BorderStroke(1.dp, Color(0xFF0E8A44))
            ) {
                Text("ADD", color = Color(0xFF0E8A44), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    val navController = rememberNavController()
    val product = ProductItem(
        imageRes = R.drawable.milk,
        name = "Mother Dairy Classic Curd",
        deliveryTime = "10 MINS",
        quantity = "400 g",
        tags = emptyList(),
        recipeCount = 0,
        price = "₹36",
        mrp = "₹38",
        discountPercentage = "5% OFF"
    )
    Box(modifier = Modifier.padding(16.dp).width(150.dp)) {
        ProductCard(product = product, navController = navController)
    }
}
