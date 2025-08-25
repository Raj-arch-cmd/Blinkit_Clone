package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.R // Assuming your R file is in this package
import kotlinx.coroutines.delay
import kotlin.math.ceil


// A simple sealed class to manage navigation routes, as seen in your code.
// A simple sealed class to manage navigation routes, as seen in your code.
sealed class Screens(val route: String) {
    // ✅ THE FIX: Added a new, unique route for the main app container
    object MainGraph : Screens("main_graph")

    object HomeScreen : Screens("HomeScreen")
    object PhoneAuthScreen : Screens("PhoneAuthScreen")
    object OrderAgainScreen : Screens("OrderAgainScreen")
    object CategoryScreen : Screens("CategoryScreen")
    object PrintScreen : Screens("PrintScreen")
    object SearchBarScreen : Screens("SearchBarScreen")
    object ProfileScreen : Screens("ProfileScreen")
    object ProductScreen : Screens("ProductScreen")
    object VerticalTabProductsScreen : Screens("VerticalTabProductsScreen")
    object FinalCheckOutScreen : Screens("FinalCheckOutScreen")
}


@Composable
fun AutoScrollingProductCarousel(navController: NavHostController) {

    val products = listOf(
        ProductItemAutoScroll(
            imageRes = R.drawable.milk,
            name = "Milk"
        ),
        ProductItemAutoScroll(
            imageRes = R.drawable.tea,
            name = "Tea"
        ),
        ProductItemAutoScroll(
            imageRes = R.drawable.kitkat,
            name = "KitKat"
        ),
        ProductItemAutoScroll(
            imageRes = R.drawable.choclate,
            name = "Chocolate"
        ),
        ProductItemAutoScroll(
            imageRes = R.drawable.dryfruits,
            name = "Dry Fruits"
        ),
        ProductItemAutoScroll(
            imageRes = R.drawable.potato,
            name = "Potato"
        ),
        ProductItemAutoScroll(
            imageRes = R.drawable.brocli,
            name = "Broccoli"
        ),
        ProductItemAutoScroll(
            imageRes = R.drawable.fruitsandvegetables,
            name = "Fruits & Veg"
        ),
        ProductItemAutoScroll(
            imageRes = R.drawable.instantfood,
            name = "Instant Food"
        ),
    )

    val scrollSpeed = 1.5f
    val spacing = 8.dp

    // Calling the auto-scrolling component
    AutoScrollingHorizontalColumn(
        products = products,
        speed = scrollSpeed,
        spacing = spacing,
        navController = navController
    )
}

@Composable
fun AutoScrollingHorizontalColumn(
    products: List<ProductItemAutoScroll>,
    speed: Float,
    spacing: Dp,
    navController: NavHostController
) {
    val scrollOffset = remember { mutableFloatStateOf(0f) }

    val itemWidth = 100.dp
    val density = LocalDensity.current
    val itemWidthPx = with(density) { itemWidth.toPx() }
    val spacingPx = with(density) { spacing.toPx() }
    val totalItemWidthPx = itemWidthPx + spacingPx

    val repeatedProducts = remember {
        List(5) { products }.flatten()
    }

    val itemsPerColumn = 3
    val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val totalContentWidth = repeatedProducts.size * totalItemWidthPx / itemsPerColumn

    LaunchedEffect(Unit) {
        while (true) {
            delay(16) // Roughly 60 frames per second

            scrollOffset.floatValue += speed
            // Reset scroll offset to create an infinite loop effect
            if (scrollOffset.floatValue >= (products.size * totalItemWidthPx / itemsPerColumn)) {
                scrollOffset.floatValue = 0f
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        val columnCount = ceil(repeatedProducts.size.toFloat() / itemsPerColumn).toInt()

        // Iterate through columns
        for (col in 0 until columnCount) {
            val xOffset = col * totalItemWidthPx - scrollOffset.floatValue

            // Only render columns that are visible or about to become visible
            if (xOffset > -totalItemWidthPx * 2 && xOffset < screenWidthPx + totalItemWidthPx * 2) {
                Column(
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = xOffset
                        }
                        .width(itemWidth),
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    // Add up to 3 items per column
                    for (row in 0 until itemsPerColumn) {
                        val index = (col * itemsPerColumn + row) % repeatedProducts.size
                        ProductCardForAutoScroll(product = repeatedProducts[index])
                    }
                }
            }
        }

        Button(
            onClick = {
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(Screens.PhoneAuthScreen.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(vertical = 20.dp, horizontal = 12.dp)
                .clip(CircleShape)
                .size(width = 80.dp, height = 35.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.8f),
                contentColor = Color.DarkGray
            )
        ) {
            Text(text = "Skip")
        }
    }
}

@Composable
fun ProductCardForAutoScroll(product: ProductItemAutoScroll) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .padding(2.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.simpleProductColor)) // Define this color in your colors.xml
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}


data class ProductItemAutoScroll(
    val imageRes: Int,
    val name: String
)

@Preview(showBackground = true)
@Composable
fun PreviewAutoScrollingProductCarousel() {
    AutoScrollingProductCarousel(navController = rememberNavController())
}