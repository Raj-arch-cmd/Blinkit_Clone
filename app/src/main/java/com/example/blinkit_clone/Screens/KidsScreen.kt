package com.example.blinkit_clone.presentation.screens.CategoryScreen

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import com.example.blinkit_clone.R

@Composable
fun KidsScreen(
    navController: NavHostController,
    listState: androidx.compose.foundation.lazy.grid.LazyGridState = androidx.compose.foundation.lazy.grid.rememberLazyGridState(),
    canLoadImages: Boolean = true
) {
    val context = LocalContext.current
    
    // Sample data for the Kids screen
    val categoryList = remember {
        listOf(
            BestSellerData("Toys & Games", listOf(R.drawable.toy1, R.drawable.toy2, R.drawable.toy4, R.drawable.toy3), "+100 more"),
            BestSellerData("Baby Care", listOf(R.drawable.babycare1, R.drawable.babycare2, R.drawable.babycare3, R.drawable.babycare4), "+60 more"),
            BestSellerData("School Supplies", listOf(R.drawable.school1, R.drawable.school2, R.drawable.school3, R.drawable.school4), "+70 more")
        )
    }
    val simpleProductItems = remember {
        listOf(
            SimpleProductItem("Diapers", "", R.drawable.diapers),
            SimpleProductItem("Baby Wipes", "", R.drawable.babywipes),
            SimpleProductItem("Kids' Snacks", "", R.drawable.babysnacks),
            SimpleProductItem("Baby Food", "", R.drawable.babyfood),
            SimpleProductItem("Pencils & Pens", "", R.drawable.penpencil),
            SimpleProductItem("Notebooks", "", R.drawable.notebook),
            SimpleProductItem("Art Supplies", "", R.drawable.artsupplies),
            SimpleProductItem("Backpacks", "", R.drawable.backpack),
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
            AsyncImage(
                model = if (canLoadImages) ImageRequest.Builder(context)
                    .data(R.drawable.kids_banner)
                    .size(1000)
                    .precision(Precision.INEXACT)
                    .crossfade(true)
                    .build() else null,
                contentDescription = "Kids Banner",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }

        item(span = { GridItemSpan(12) }) {
            Text(
                text = "For the Little Ones",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        items(categoryList, span = { GridItemSpan(4) }) { item ->
            BestSellerComponent(works = item, navController = navController, canLoadImages = canLoadImages)
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
            SimpleProductCard(product = item, navController = navController, canLoadImages = canLoadImages)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KidsScreenPreview() {
    KidsScreen(navController = rememberNavController())
}
