package com.example.blinkit_clone.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BestSellerComponent
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BestSellerData
import com.example.blinkit_clone.presentation.screens.CategoryScreen.SimpleProductCard
import com.example.blinkit_clone.presentation.screens.CategoryScreen.SimpleProductItem

@Composable
fun BeautyScreen(navController: NavHostController) {
    // Sample data for the Beauty screen
    val categoryList = listOf(
        BestSellerData("Skin Care", listOf(R.drawable.skincare1, R.drawable.skincare2, R.drawable.skincare3, R.drawable.skincare4), "+50 more"),
        BestSellerData("Hair Care", listOf(R.drawable.haircare1, R.drawable.haircare2, R.drawable.haircare3, R.drawable.haircare4), "+40 more"),
        BestSellerData("Makeup", listOf(R.drawable.makeup1, R.drawable.makeup2, R.drawable.makeup3, R.drawable.makeup4), "+80 more")
    )
    val simpleProductItems = listOf(
        SimpleProductItem("Lipsticks", "", R.drawable.lipsticks),
        SimpleProductItem("Face Masks", "", R.drawable.facemask),
        SimpleProductItem("Shampoos", "", R.drawable.shampoos),
        SimpleProductItem("Conditioners", "", R.drawable.conditioners),
        SimpleProductItem("Fragrances", "", R.drawable.perfumes),
        SimpleProductItem("Serums", "", R.drawable.serums),
        SimpleProductItem("Sunscreens", "", R.drawable.sunscreens),
        SimpleProductItem("Cleansers", "", R.drawable.cleansers),
        // --- New Row 1 ---
        SimpleProductItem("Moisturizers", "", R.drawable.moisturiser),
        SimpleProductItem("Toners", "", R.drawable.toner),
        SimpleProductItem("Eyeliners", "", R.drawable.eyeliner),
        SimpleProductItem("Foundations", "", R.drawable.foundation),
        // --- New Row 2 ---
        SimpleProductItem("Nail Polish", "", R.drawable.nailpolish),
        SimpleProductItem("Body Wash", "", R.drawable.bodywash),
        SimpleProductItem("Lotions", "", R.drawable.lotion),
        SimpleProductItem("Hair Oils", "", R.drawable.hairoil)
    )

    // ✅ THE FIX: Removed the verticalScroll modifier to prevent a crash.
    // The parent screen (HomeScreen) will handle the scrolling.
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.beauty_banner),
            contentDescription = "Beauty Banner",
            modifier = Modifier.fillMaxWidth(),
        )

        // --- Beauty Bestsellers Section ---
        Text(
            text = "Beauty Bestsellers",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )
        categoryList.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        BestSellerComponent(works = item, navController = navController)
                    }
                }
                if (rowItems.size < 3) {
                    for (i in 0 until (3 - rowItems.size)) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // --- Shop by Category Section ---
        Text(
            text = "Shop by Category",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
        )
        simpleProductItems.chunked(4).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp), // Added vertical padding for spacing between rows
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        SimpleProductCard(product = item, navController)
                    }
                }
                // This spacer is no longer needed if all rows are full
                if (rowItems.size < 4) {
                    for (i in 0 until (4 - rowItems.size)) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 16.dp)) // Add space at the very bottom
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BeautyScreenPreview() {
    BeautyScreen(navController = rememberNavController())
}

