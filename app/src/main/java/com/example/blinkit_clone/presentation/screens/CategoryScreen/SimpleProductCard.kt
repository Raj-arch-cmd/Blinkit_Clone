package com.example.blinkit_clone.presentation.screens.CategoryScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.blinkit_clone.R

data class SimpleProductItem(
    val name1: String,
    val name2: String,
    val imageRes: Int
)

@Composable
fun SimpleProductCard(product: SimpleProductItem, navController: NavHostController) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .size(85.dp)
                .clickable {
                    try {
                        Log.d("SimpleProductCard", "Navigating to product detail: ${Screens.ProductScreen.route}")
                        navController.navigate(Screens.ProductScreen.route)
                    } catch (e: Exception) {
                        Log.e("SimpleProductCard", "Navigation failed: ${e.message}")
                    }
                },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.simpleProductColor))
        ) {
            AsyncImage(
                model = product.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.name1,
            fontSize = 12.sp, lineHeight = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = product.name2,
            fontSize = 12.sp, lineHeight = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
