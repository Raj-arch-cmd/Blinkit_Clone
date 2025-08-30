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
fun ElectronicsScreen(navController: NavHostController) {
    val categoryList = listOf<BestSellerData>(
        BestSellerData("Mobiles & Accessories", listOf(R.drawable.mobiles, R.drawable.cableandchargers, R.drawable.powerbank, R.drawable.smartwatch), "+20 more"),
        BestSellerData("Laptops & Computers", listOf(R.drawable.laptops, R.drawable.keyboard, R.drawable.mouse, R.drawable.monitor), "+15 more"),
        BestSellerData("Speakers & Headphones", listOf(R.drawable.speakers, R.drawable.headphones, R.drawable.speakers, R.drawable.headphones), "+30 more"),
    )
    val simpleProductItems = listOf<SimpleProductItem>(
        SimpleProductItem("Smart Watches", "", R.drawable.smartwatch),
        SimpleProductItem("Headphones", "", R.drawable.headphones),
        SimpleProductItem("Power Banks", "", R.drawable.powerbank),
        SimpleProductItem("Cables & Chargers", "", R.drawable.cableandchargers),
        SimpleProductItem("Smart Home", "", R.drawable.smarthome),
        SimpleProductItem("Trimmers", "", R.drawable.trimmer),
        SimpleProductItem("Hair Dryers", "", R.drawable.hairdryer),
        SimpleProductItem("Extension Cords", "", R.drawable.extensioncord),
        SimpleProductItem("Batteries", "", R.drawable.batteries),
        SimpleProductItem("LED Bulbs", "", R.drawable.ledbulbs),
        SimpleProductItem("Adapters", "", R.drawable.adapter),
        SimpleProductItem("Mouse", "", R.drawable.mouse),
        SimpleProductItem("Keyboards", "", R.drawable.keyboard),
        SimpleProductItem("Monitors", "", R.drawable.monitor),
        SimpleProductItem("Printers", "", R.drawable.printers),
        SimpleProductItem("Webcams", "", R.drawable.webcam),
    )

    // ✅ THE FIX: The entire screen is now a simple Column.
    // The scrolling is handled by the parent LazyColumn in HomeScreen.kt.
    Column(modifier = Modifier.fillMaxWidth()) {
        // --- Banner ---
        Image(
            painter = painterResource(id = R.drawable.electronics_banner),
            contentDescription = "Electronics Banner",
            modifier = Modifier.fillMaxWidth(),
        )

        // --- Top Electronics Section ---
        Text(
            text = "Top Electronics",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )
        // Manually create a grid using Rows
        categoryList.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        BestSellerComponent(works = item, navController = navController)
                    }
                }
                // Add spacers to keep alignment if the row isn't full
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
        // Manually create a grid using Rows
        simpleProductItems.chunked(4).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        SimpleProductCard(product = item, navController)
                    }
                }
                if (rowItems.size < 4) {
                    for (i in 0 until (4 - rowItems.size)) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ElectronicsScreenPreview() {
    val navController = rememberNavController()
    ElectronicsScreen(navController = navController)
}
