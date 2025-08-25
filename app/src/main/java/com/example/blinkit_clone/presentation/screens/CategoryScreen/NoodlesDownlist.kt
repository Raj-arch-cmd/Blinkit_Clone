package com.example.blinkit_clone.presentation.screens.CategoryScreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkit_clone.R



// Note: The definitions for MaggiNoodleItem() and SeeProductCard()
// are in other files and are not shown in the images.

@Composable
fun NoodlesDownList() {
    Column {
        Text(
            text = "Noodles",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            fontSize = 18.sp,
            letterSpacing = 1.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MaggiNoodleItem(R.drawable.milk, "Maggi Masala - 2 Minutes Instant Noodles")
            MaggiNoodleItem(R.drawable.milk, "Maggi Masala - 2 Minutes Instant Noodles")
            MaggiNoodleItem(R.drawable.milk, "Maggi Masala - 2 Minutes Instant Noodles")
        }

        SeeProductCard()
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Soft Drinks",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            fontSize = 18.sp,
            letterSpacing = 1.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MaggiNoodleItem(R.drawable.milk, "Pepsi Hip Soft Drink(750 ml)")
            MaggiNoodleItem(R.drawable.milk, "Coca-Cola Diet Coke Diets & Lights Pack")
            MaggiNoodleItem(R.drawable.milk, "Sprite Lime Flavored Soft Drink 750 ml")

        }
    }
}
@Composable
fun MaggiNoodleItem(imageRes: Int, title: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
             , // helps items share equal width
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.Image(
            painter = androidx.compose.ui.res.painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier
                .height(80.dp)
        )
        Text(
            text = title,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun SeeProductCard() {
    Text(
        text = "See All Products",
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    )
}

