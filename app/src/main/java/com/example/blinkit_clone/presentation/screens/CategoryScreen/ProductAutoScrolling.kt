package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import com.example.blinkit_clone.R
import kotlin.math.ceil

@Composable
fun AutoScrollingProductCarousel(
    speed: Float = 0.8f,
    spacing: Dp = 12.dp,
    canLoadImages: Boolean = true // ✅ NEW: Defer decoding
) {
    val products = remember {
        listOf(
            ProductItemAutoScroll(R.drawable.milk, "Milk"),
            ProductItemAutoScroll(R.drawable.tea, "Tea"),
            ProductItemAutoScroll(R.drawable.kitkat, "KitKat"),
            ProductItemAutoScroll(R.drawable.choclate, "Chocolate"),
            ProductItemAutoScroll(R.drawable.dryfruits, "Dry Fruits"),
            ProductItemAutoScroll(R.drawable.potato, "Potato"),
            ProductItemAutoScroll(R.drawable.brocli, "Broccoli"),
            ProductItemAutoScroll(R.drawable.fruitsandvegetables, "Fruits"),
            ProductItemAutoScroll(R.drawable.instantfood, "Instant"),
        )
    }

    AutoScrollingHorizontalColumn(
        products = products,
        speed = speed,
        spacing = spacing,
        canLoadImages = canLoadImages
    )
}

@Composable
fun AutoScrollingHorizontalColumn(
    products: List<ProductItemAutoScroll>,
    speed: Float,
    spacing: Dp,
    canLoadImages: Boolean
) {
    val scrollOffset = remember { mutableFloatStateOf(0f) }
    val itemWidth = 110.dp
    val density = LocalDensity.current
    val itemWidthPx = with(density) { itemWidth.toPx() }
    val spacingPx = with(density) { spacing.toPx() }
    val totalItemWidthPx = itemWidthPx + spacingPx

    val itemsPerColumn = 3
    val repeatedProducts = remember { List(10) { products }.flatten() }
    
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { _ ->
                scrollOffset.floatValue += speed
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val columnCount = ceil(repeatedProducts.size.toFloat() / itemsPerColumn).toInt()
        val totalWidth = columnCount * totalItemWidthPx

        var actuallyComposed = 0

        for (col in 0 until columnCount) {
            val baseOffset = col * totalItemWidthPx
            
            // ✅ THE FIX: Calculate horizontal position for visibility culling
            // We do this before composition to decide if we should emit this Column at all.
            var currentX = (baseOffset - scrollOffset.floatValue) % totalWidth
            if (currentX < -totalItemWidthPx) currentX += totalWidth

            // ✅ VISIBILITY CULLING: Only compose if the column is visible or within a small buffer
            if (currentX > -totalItemWidthPx && currentX < screenWidthPx + totalItemWidthPx) {
                actuallyComposed++
                Column(
                    modifier = Modifier
                        .graphicsLayer { translationX = currentX }
                        .width(itemWidth)
                        .padding(top = if (col % 2 == 0) 0.dp else 40.dp),
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    for (row in 0 until itemsPerColumn) {
                        ProductCardForAutoScroll(
                            product = repeatedProducts[(col * itemsPerColumn + row) % repeatedProducts.size],
                            canLoadImages = canLoadImages
                        )
                    }
                }
            }
        }
        
        // Final verification log removed for production performance
    }
}

@Composable
fun ProductCardForAutoScroll(
    product: ProductItemAutoScroll,
    canLoadImages: Boolean
) {
    val context = LocalContext.current
    
    Card(
        modifier = Modifier
            .size(110.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // ✅ THE FIX: Zero elevation for background items
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AsyncImage(
                model = if (canLoadImages) ImageRequest.Builder(context)
                    .data(product.imageRes)
                    .size(200) 
                    .precision(Precision.INEXACT)
                    .crossfade(true)
                    .build() else null,
                contentDescription = product.name,
                modifier = Modifier.fillMaxSize().padding(12.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Immutable
data class ProductItemAutoScroll(val imageRes: Int, val name: String)
