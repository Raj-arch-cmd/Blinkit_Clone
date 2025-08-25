package com.example.blinkit_clone.presentation.screens.CategoryScreen



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


// MANUALLY ADD THIS LINE
import com.example.blinkit_clone.R



// Data class to hold information for the component
data class BestSellerData(
    val title: String,
    val imageResids: List<Int>,
    val label: String
)

@Composable
fun BestSellerComponent(works: BestSellerData, navController: NavHostController) {
    // The main Card for the component
    Card(
        modifier = Modifier
            .size(width = 120.dp, height = 180.dp)
            .padding(horizontal = 6.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(alpha = 0.3f)
        )
    ) {
        // Use a new parent Box to layer the label on top of the content
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            // Your original content (images and title) goes inside a Column
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp) // Adjusted height for the image grid
                        .padding(4.dp)
                        .clickable {
                            // navController.navigate(...)
                        }
                ) {
                    // ... Keep your 4 Image Boxes exactly as they were ...
                }

                // Title Text
                Text(
                    text = works.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), // Add some padding
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

            // **THE FIX:** Place the label Card here, aligned to the TopCenter
            // of the new parent Box.
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter) // Align to the top
                    .padding(top = 8.dp), // Add padding to bring it down slightly
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(
                    text = works.label,
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BestSellerComponentPreview() {
    val fakeNavController = rememberNavController()
    BestSellerComponent(
        works = BestSellerData(
            title = "Top Picks",
            imageResids = listOf(
                R.drawable.milk,
                R.drawable.tea,
                R.drawable.choclate,
                R.drawable.kitkat
            ),
            label = "Popular"
        ),
        navController = fakeNavController
    )
}
