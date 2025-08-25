package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkit_clone.R // Ensure this R import is correct for your project

@Composable
fun ProductItemCard() {
    Card(
        modifier = Modifier
            .width(180.dp), // Increased width slightly for new elements
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            // --- Bestseller Badge and Product Image ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.milk),
                    contentDescription = "Product Image",
                    modifier = Modifier.fillMaxSize()
                )
                // Bestseller Badge
                Card(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF8E479).copy(alpha = 0.9f)
                    )
                ) {
                    Text(
                        text = "Bestseller",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B572A),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            // --- Product Details ---
            Text(
                text = "200 gm",
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Maggie Masala - 2 \nMinutes Instant Noodles",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            // --- Star Rating ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 5 Stars
                repeat(5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                // Rating count
                Text(
                    text = "(88,764)",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            // --- Delivery Time ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.WatchLater,
                    contentDescription = "Delivery time",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "15 MINS",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(modifier = Modifier.height(12.dp))

            // --- Price and Add Button Row ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Price Column
                Column {
                    Text(
                        text = "₹51",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "MRP ₹63",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.LineThrough // Strikethrough effect
                    )
                }

                // Add Button
                QuantityAddButton()
            }
        }
    }
}

@Composable
fun QuantityAddButton() {
    val greenColor = Color(0xFF0E8A44)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(greenColor)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = "—",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = "1",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProductItemCardPreview() {
    ProductItemCard()
}