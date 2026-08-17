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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.blinkit_clone.R

@Composable
fun SummerCategoryScreen(
    navController: NavHostController,
    listState: androidx.compose.foundation.lazy.grid.LazyGridState = androidx.compose.foundation.lazy.grid.rememberLazyGridState()
){

    //Simple Product Cards
    val simpleProductItems = remember {
        listOf(
            SimpleProductItem(
                name1 = "Ice Creams &",
                name2 = "More",
                imageRes = R.drawable.milk
            ),
            SimpleProductItem(
                name1 = "Soft Drinks &",
                name2 = "Juices",
                imageRes = R.drawable.milk
            ),
            SimpleProductItem(
                name1 = "Cold Coffee &",
                name2 = "Iced Tea",
                imageRes = R.drawable.milk
            ),
            SimpleProductItem(
                name1 = "Dairy, Bread &",
                name2 = "Eggs",
                imageRes = R.drawable.milk
            ),
            SimpleProductItem(
                name1 = "Hydration",
                name2 = "Essentials",
                imageRes = R.drawable.milk
            ),
            SimpleProductItem(
                name1 = "Fresh Fruits",
                name2 = "",
                imageRes = R.drawable.milk
            ),
            SimpleProductItem(
                name1 = "MilkShakes",
                name2 = "",
                imageRes = R.drawable.milk
            ),
            SimpleProductItem(
                name1 = "Soft Drinks",
                name2 = "",
                imageRes = R.drawable.milk
            )
        )
    }
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 80.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(4) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Background banner image
                AsyncImage(
                    model = R.drawable.summerbanner,
                    contentDescription = "summer banner",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }

        item(span = { GridItemSpan(4) }) {
            Text(
                text = "Be ready for sunny days",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        items(simpleProductItems.take(4)) { items ->
            SimpleProductCard(product = items, navController = navController)
        }

        item(span = { GridItemSpan(4) }) {
            Text(
                text = "Summer coolers",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        items(simpleProductItems) { items ->
            SimpleProductCard(product = items, navController = navController)
        }

        item(span = { GridItemSpan(4) }) {
            Text(
                text = "Stay fit & just chill",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        items(simpleProductItems.take(4)) { items ->
            SimpleProductCard(product = items, navController = navController)
        }
    }
}
