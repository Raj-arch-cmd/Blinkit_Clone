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
import com.example.blinkit_clone.R.drawable.school4
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BestSellerComponent
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BestSellerData
import com.example.blinkit_clone.presentation.screens.CategoryScreen.SimpleProductCard
import com.example.blinkit_clone.presentation.screens.CategoryScreen.SimpleProductItem

@Composable
fun KidsScreen(navController: NavHostController) {
    // Sample data for the Kids screen
    val categoryList = listOf(
        BestSellerData("Toys & Games", listOf(R.drawable.toy1, R.drawable.toy2, R.drawable.toy4, R.drawable.toy3), "+100 more"),
        BestSellerData("Baby Care", listOf(R.drawable.babycare1, R.drawable.babycare2, R.drawable.babycare3, R.drawable.babycare4), "+60 more"),
        BestSellerData("School Supplies", listOf(R.drawable.school1, R.drawable.school2, R.drawable.school3, school4), "+70 more")
    )
    val simpleProductItems = listOf(
        SimpleProductItem("Diapers", "", R.drawable.diapers),
        SimpleProductItem("Baby Wipes", "", R.drawable.babywipes),
        SimpleProductItem("Kids' Snacks", "", R.drawable.babysnacks),
        SimpleProductItem("Baby Food", "", R.drawable.babyfood),
        SimpleProductItem("Pencils & Pens", "", R.drawable.penpencil),
        SimpleProductItem("Notebooks", "", R.drawable.notebook),
        SimpleProductItem("Art Supplies", "", R.drawable.artsupplies),
        SimpleProductItem("Backpacks", "", R.drawable.backpack),
    )

    // ✅ THE FIX: The root is now a non-scrollable Column.
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.kids_banner),
            contentDescription = "Kids Banner",
            modifier = Modifier.fillMaxWidth(),
        )

        // --- For the Little Ones Section ---
        Text(
            text = "For the Little Ones",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )
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
fun KidsScreenPreview() {
    KidsScreen(navController = rememberNavController())
}
