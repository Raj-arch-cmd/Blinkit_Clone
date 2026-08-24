package com.example.blinkit_clone.presentation.screens.CategoryScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import com.example.blinkit_clone.R


@Composable
fun AllCategoryScreen(
    navController: NavHostController,
    listState: androidx.compose.foundation.lazy.grid.LazyGridState = androidx.compose.foundation.lazy.grid.rememberLazyGridState(),
    canLoadImages: Boolean = true
) {
    val context = LocalContext.current
    
    
    //BestSeller Cards
    val categoryList = remember {
        listOf<BestSellerData>(
            BestSellerData(
                title = "Dairy Bread & Eggs",
                imageResids = listOf(
                    R.drawable.milk,
                    R.drawable.tea,
                    R.drawable.choclate,
                    R.drawable.kitkat
                ),
                label = "+11 more"
            ),

            BestSellerData(
                title = "Vegetable & Fruits",
                imageResids = listOf(
                    R.drawable.began,
                    R.drawable.carrot,
                    R.drawable.brocli,
                    R.drawable.cabbage
                ),
                label = "+56 more"
            ),

            BestSellerData(
                title = "Oil Ghee & Masala",
                imageResids = listOf(
                    R.drawable.choclate,
                    R.drawable.tea,
                    R.drawable.milk,
                    R.drawable.cabbage
                ),
                label = "+118 more"
            ),

            BestSellerData(
                title = "Chips & Namkeen",
                imageResids = listOf(
                    R.drawable.lays,
                    R.drawable.instantfood,
                    R.drawable.milk,
                    R.drawable.kitkat
                ),
                label = "+89 more"
            ),

            BestSellerData(
                title = "Atta Rice- & Dal",
                imageResids = listOf(
                    R.drawable.fruitsandvegetables,
                    R.drawable.choclate,
                    R.drawable.tea,
                    R.drawable.knoppers
                ),
                label = "+158 more"
            ),

            BestSellerData(
                title = "Dry Fruits & Cereals",
                imageResids = listOf(
                    R.drawable.dryfruits,
                    R.drawable.milk,
                    R.drawable.dryfruits,
                    R.drawable.choclate
                ),
                label = "+250 more"
            )
        )
    }

    //Simple Product Cards
    val simpleProductItems = remember {
        listOf(
            SimpleProductItem(
                name1 = "Vegetables &",
                name2 = "Fruits",
                imageRes = R.drawable.fruitsandvegetables
            ),
            SimpleProductItem(
                name1 = "Atta, Rice &",
                name2 = "Dal",
                imageRes = R.drawable.instantfood
            ),
            SimpleProductItem(
                name1 = "Oil,Ghee &",
                name2 = "Masala",
                imageRes = R.drawable.dryfruits
            ),
            SimpleProductItem(
                name1 = "Dairy, Bread &",
                name2 = "Eggs",
                imageRes = R.drawable.milk
            ),
            SimpleProductItem(
                name1 = "Dairy, Bread &",
                name2 = "Eggs",
                imageRes = R.drawable.began
            ),
            SimpleProductItem(
                name1 = "Dairy, Bread &",
                name2 = "Eggs",
                imageRes = R.drawable.garlic
            ),
            SimpleProductItem(
                name1 = "Dairy, Bread &",
                name2 = "Eggs",
                imageRes = R.drawable.potato
            ),
            SimpleProductItem(
                name1 = "Dairy, Bread &",
                name2 = "Eggs",
                imageRes = R.drawable.cabbage
            )
        )
    }
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(12),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 80.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(12) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Background banner image
                AsyncImage(
                    model = if (canLoadImages) ImageRequest.Builder(context)
                        .data(R.drawable.allwinterbanner)
                        .size(1000) // Constrain large banner (px)
                        .precision(Precision.INEXACT)
                        .crossfade(true)
                        .build() else null,
                    contentDescription = "All winter banner",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }

        item(span = { GridItemSpan(12) }) {
            Text(
                text = "Bestsellers",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        items(categoryList, span = { GridItemSpan(4) }) { works ->
            BestSellerComponent(works = works, navController = navController, canLoadImages = canLoadImages)
        }

        item(span = { GridItemSpan(12) }) {
            Text(
                text = "Grocery & Kitchen",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        items(simpleProductItems, span = { GridItemSpan(3) }) { items ->
            SimpleProductCard(product = items, navController = navController, canLoadImages = canLoadImages)
        }

        item(span = { GridItemSpan(12) }) {
            Text(
                text = "Snacks & Drinks",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        items(simpleProductItems, span = { GridItemSpan(3) }) { items ->
            SimpleProductCard(product = items, navController = navController, canLoadImages = canLoadImages)
        }

        item(span = { GridItemSpan(12) }) {
            Text(
                text = "Beauty & Personal Care",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        items(simpleProductItems, span = { GridItemSpan(3) }) { items ->
            SimpleProductCard(product = items, navController = navController, canLoadImages = canLoadImages)
        }

        item(span = { GridItemSpan(12) }) {
            Text(
                text = "Household Essentials",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        items(simpleProductItems, span = { GridItemSpan(3) }) { items ->
            SimpleProductCard(product = items, navController = navController, canLoadImages = canLoadImages)
        }
    }
}
