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
fun BeautyScreen(
    navController: NavHostController,
    listState: androidx.compose.foundation.lazy.grid.LazyGridState = androidx.compose.foundation.lazy.grid.rememberLazyGridState()
) {
    // Sample data for the Beauty screen
    val categoryList = remember {
        listOf(
            BestSellerData("Skin Care", listOf(R.drawable.skincare1, R.drawable.skincare2, R.drawable.skincare3, R.drawable.skincare4), "+50 more"),
            BestSellerData("Hair Care", listOf(R.drawable.haircare1, R.drawable.haircare2, R.drawable.haircare3, R.drawable.haircare4), "+40 more"),
            BestSellerData("Makeup", listOf(R.drawable.makeup1, R.drawable.makeup2, R.drawable.makeup3, R.drawable.makeup4), "+80 more")
        )
    }
    val simpleProductItems = remember {
        listOf(
            SimpleProductItem("Lipsticks", "", R.drawable.lipsticks),
            SimpleProductItem("Face Masks", "", R.drawable.facemask),
            SimpleProductItem("Shampoos", "", R.drawable.shampoos),
            SimpleProductItem("Conditioners", "", R.drawable.conditioners),
            SimpleProductItem("Fragrances", "", R.drawable.perfumes),
            SimpleProductItem("Serums", "", R.drawable.serums),
            SimpleProductItem("Sunscreens", "", R.drawable.sunscreens),
            SimpleProductItem("Cleansers", "", R.drawable.cleansers),
            SimpleProductItem("Moisturizers", "", R.drawable.moisturiser),
            SimpleProductItem("Toners", "", R.drawable.toner),
            SimpleProductItem("Eyeliners", "", R.drawable.eyeliner),
            SimpleProductItem("Foundations", "", R.drawable.foundation),
            SimpleProductItem("Nail Polish", "", R.drawable.nailpolish),
            SimpleProductItem("Body Wash", "", R.drawable.bodywash),
            SimpleProductItem("Lotions", "", R.drawable.lotion),
            SimpleProductItem("Hair Oils", "", R.drawable.hairoil)
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
                painter = painterResource(id = R.drawable.beauty_banner),
                contentDescription = "Beauty Banner",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }

        item(span = { GridItemSpan(12) }) {
            Text(
                text = "Beauty Bestsellers",
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
fun BeautyScreenPreview() {
    BeautyScreen(navController = rememberNavController())
}
