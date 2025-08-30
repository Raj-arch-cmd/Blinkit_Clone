package com.example.blinkit_clone.presentation.screens.CategoryScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.blinkit_clone.R

// Data class to hold information for the component
data class BestSellerData(
    val title: String,
    val imageResids: List<Int>,
    val label: String
)

@Composable
fun BestSellerComponent(works: BestSellerData, navController: NavHostController) {
    // ✅ THE FIX: Added logging to debug the crash.
    // This will print the title and the number of images for each card in Logcat.
    Log.d("BestSellerComponent", "Composing card for '${works.title}' with ${works.imageResids.size} images.")

    Card(
        modifier = Modifier
            .size(width = 120.dp, height = 180.dp)
            .padding(horizontal = 6.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(alpha = 0.3f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(8.dp)
                        .clickable { /* Handle navigation */ }
                ) {
                    // This grid is now safe. It checks if an image exists
                    // before trying to display it, which prevents crashes.
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (works.imageResids.isNotEmpty()) {
                                Image(painter = painterResource(id = works.imageResids[0]), contentDescription = null, modifier = Modifier.weight(1f))
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            if (works.imageResids.size > 1) {
                                Image(painter = painterResource(id = works.imageResids[1]), contentDescription = null, modifier = Modifier.weight(1f))
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (works.imageResids.size > 2) {
                                Image(painter = painterResource(id = works.imageResids[2]), contentDescription = null, modifier = Modifier.weight(1f))
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            if (works.imageResids.size > 3) {
                                Image(painter = painterResource(id = works.imageResids[3]), contentDescription = null, modifier = Modifier.weight(1f))
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                Text(
                    text = works.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp),
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
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
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
}
