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
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BestSellerComponent
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BestSellerData
import com.example.blinkit_clone.presentation.screens.CategoryScreen.SimpleProductCard
import com.example.blinkit_clone.presentation.screens.CategoryScreen.SimpleProductItem
import com.example.blinkit_clone.R


@Composable
fun AllCategoryScreen(navController: NavHostController) {
    //BestSeller Cards
    val categoryList = listOf<BestSellerData>(
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

    //Simple Product Cards
    val simpleProductItems = listOf(
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
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Background banner image
            Image(
                painter = painterResource(id = R.drawable.allwinterbanner),
                contentDescription = "All winter banner",
                modifier = Modifier
                    .fillMaxSize(),
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Bestsellers",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            LazyVerticalGrid(
                userScrollEnabled = false,
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 8.dp),
                modifier = Modifier.fillMaxWidth().height(350.dp),
                content = {
                    items(categoryList) { works ->
                        BestSellerComponent(works = works,navController = navController)
                    }
                }
            )
            Text(
                text = "Grocery & Kitchen",
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
                text = "Snacks & Drinks",
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
                text = "Beauty & Personal Care",
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
                text = "Household Essentials",
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
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}
