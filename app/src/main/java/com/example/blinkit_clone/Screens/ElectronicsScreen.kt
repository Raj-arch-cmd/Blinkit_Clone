package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.R

@Composable
fun ElectronicsScreen(
    navController: NavHostController,
    listState: androidx.compose.foundation.lazy.grid.LazyGridState = androidx.compose.foundation.lazy.grid.rememberLazyGridState()
) {
    val categoryList = remember {
        listOf(
            BestSellerData("Mobiles & Accessories", listOf(R.drawable.mobiles, R.drawable.cableandchargers, R.drawable.powerbank, R.drawable.smartwatch), "+20 more"),
            BestSellerData("Laptops & Computers", listOf(R.drawable.laptops, R.drawable.keyboard, R.drawable.mouse, R.drawable.monitor), "+15 more"),
            BestSellerData("Speakers & Headphones", listOf(R.drawable.speakers, R.drawable.headphones, R.drawable.speakers, R.drawable.headphones), "+30 more"),
        )
    }
    val simpleProductItems = remember {
        listOf(
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
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(12),
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 80.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(12) }) {
            Image(
                painter = painterResource(id = R.drawable.electronics_banner),
                contentDescription = "Electronics Banner",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }

        item(span = { GridItemSpan(12) }) {
            Text(
                text = "Top Electronics",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        items(categoryList, span = { GridItemSpan(4) }) { item ->
            BestSellerComponent(works = item, navController = navController)
        }

        item(span = { GridItemSpan(12) }) {
            Text(
                text = "Shop by Category",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
            )
        }

        items(simpleProductItems, span = { GridItemSpan(3) }) { item ->
            SimpleProductCard(product = item, navController = navController)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ElectronicsScreenPreview() {
    val navController = rememberNavController()
    ElectronicsScreen(navController = navController)
}
