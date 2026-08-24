package com.example.blinkit_clone.presentation.screens.CategoryScreen

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import androidx.compose.runtime.Immutable
import com.example.blinkit_clone.R

// Data class to hold information for the component
@Immutable
data class BestSellerData(
    val title: String,
    val imageResids: List<Int>,
    val label: String
)

@Composable
fun BestSellerComponent(
    works: BestSellerData,
    navController: NavHostController,
    canLoadImages: Boolean = true
) {
    val context = LocalContext.current
    
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
                        .clickable {
                            try {
                                navController.navigate(Screens.ProductScreen.route)
                            } catch (e: Exception) {
                                android.util.Log.e("BestSellerComponent", "Navigation failed: ${e.message}")
                            }
                        }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (works.imageResids.isNotEmpty()) {
                                AsyncImage(
                                    model = if (canLoadImages) ImageRequest.Builder(context)
                                        .data(works.imageResids[0])
                                        .size(150) // Explicitly request small size (px)
                                        .precision(Precision.INEXACT)
                                        .crossfade(true)
                                        .build() else null,
                                    contentDescription = null,
                                    modifier = Modifier.weight(1f),
                                    contentScale = ContentScale.Fit
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            if (works.imageResids.size > 1) {
                                AsyncImage(
                                    model = if (canLoadImages) ImageRequest.Builder(context)
                                        .data(works.imageResids[1])
                                        .size(150)
                                        .precision(Precision.INEXACT)
                                        .crossfade(true)
                                        .build() else null,
                                    contentDescription = null,
                                    modifier = Modifier.weight(1f),
                                    contentScale = ContentScale.Fit
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (works.imageResids.size > 2) {
                                AsyncImage(
                                    model = if (canLoadImages) ImageRequest.Builder(context)
                                        .data(works.imageResids[2])
                                        .size(150)
                                        .precision(Precision.INEXACT)
                                        .crossfade(true)
                                        .build() else null,
                                    contentDescription = null,
                                    modifier = Modifier.weight(1f),
                                    contentScale = ContentScale.Fit
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            if (works.imageResids.size > 3) {
                                AsyncImage(
                                    model = if (canLoadImages) ImageRequest.Builder(context)
                                        .data(works.imageResids[3])
                                        .size(150)
                                        .precision(Precision.INEXACT)
                                        .crossfade(true)
                                        .build() else null,
                                    contentDescription = null,
                                    modifier = Modifier.weight(1f),
                                    contentScale = ContentScale.Fit
                                )
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
