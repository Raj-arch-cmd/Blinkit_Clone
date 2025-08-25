package com.example.blinkit_clone.presentation.screens.CategoryScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkit_clone.R

@Composable
fun ViewCartButtonExact() {
    FloatingActionButton(
        onClick = { /* No action performed */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(60.dp), // Set a fixed height
        shape = RoundedCornerShape(50), // Capsule shape
        containerColor = Color(0xFF0E8A44) // Main green color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Part 1: White Box with just the Image
            Card(
                shape = RoundedCornerShape(50.dp), // Adjusted corner for a cleaner look
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.padding(vertical = 6.dp)
            ) {
                // The image is now the only item in the card
                Image(
                    painter = painterResource(id = R.drawable.milk),
                    contentDescription = "Item Image",
                    modifier = Modifier
                        .size(40.dp) // Made slightly larger to fill the space
                        .padding(8.dp)
                )
            }

            Spacer(Modifier.width(16.dp))

            // Part 2: "View cart" and "1 ITEM" texts stacked in a Column
            Column {
                Text(
                    text = "View cart",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "1 ITEM",
                    fontSize = 12.sp, // Adjusted font size
                    fontWeight = FontWeight.Normal, // Adjusted font weight
                    color = Color.White // Changed to white to be visible
                )
            }

            Spacer(Modifier.weight(1f)) // Pushes the arrow to the far right

            // Part 3: Arrow in a darker circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF08642E), CircleShape), // Darker green circle
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Go to cart",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewCartButtonExactPreview() {
    // Add a Box to constrain the size in the preview
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.BottomCenter) {
        ViewCartButtonExact()
    }
}