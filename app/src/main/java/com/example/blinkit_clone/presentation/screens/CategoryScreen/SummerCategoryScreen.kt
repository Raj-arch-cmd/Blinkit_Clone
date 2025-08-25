package com.example.blinkit_clone.presentation.CategoryScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.blinkit_clone.presentation.screens.CategoryScreen.SimpleProductCard
import com.example.blinkit_clone.presentation.screens.CategoryScreen.SimpleProductItem
import com.example.blinkit_clone.R

@Composable
fun SummerCategoryScreen(navController: NavHostController){

    //Simple Product Cards
    val simpleProductItems = listOf(
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
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Background banner image
            Image(
                painter = painterResource(id = R.drawable.summerbanner),
                contentDescription = "summer banner",
                modifier = Modifier
                    .fillMaxSize(),
            )
        }
        Text(
            text = "Be ready for sunny days",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
        LazyVerticalGrid(
            userScrollEnabled = false,
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier.fillMaxWidth().height(135.dp),
            content = {
                items(simpleProductItems) { items ->
                    SimpleProductCard(product = items,navController)
                }
            }
        )
        Text(
            text = "Summer coolers",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
        LazyVerticalGrid(
            userScrollEnabled = false,
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier.fillMaxWidth().height(300.dp),
            content = {
                items(simpleProductItems) { items ->
                    SimpleProductCard(product = items,navController)
                }
            }
        )

        Text(
            text = "Stay fit & just chill",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyVerticalGrid(
            userScrollEnabled = false,
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier.fillMaxWidth().height(135.dp),
            content = {
                items(simpleProductItems) { items ->
                    SimpleProductCard(product = items,navController)
                }
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
    }
}
