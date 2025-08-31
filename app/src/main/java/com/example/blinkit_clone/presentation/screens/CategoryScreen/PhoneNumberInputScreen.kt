package com.example.blinkit_clone.presentation.screens


import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.presentation.screens.CategoryScreen.Screens
import com.example.blinkit_clone.ui.theme.PhoneAuthViewModel
import kotlinx.coroutines.delay
import kotlin.math.ceil


@Composable
fun PhoneNumberInputScreen(
    viewModel: PhoneAuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var phoneNumber by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as ComponentActivity

    // Observe the auth state to manage loading indicator
    val authState by viewModel.authState.collectAsState()
    LaunchedEffect(authState) {
        isLoading = false // Reset loading state when auth state changes
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // This is our auto-scrolling product carousel
            AutoScrollingProductCarousel(navController = navController)

            Spacer(modifier = Modifier.height(40.dp))

            // Blinkit Logo
            Box(
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFFFC107)) // Corrected color
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Blinkit",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "India's last minute app",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Enter your phone number to continue",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                placeholder = { Text("+91 12345 67890") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (phoneNumber.isNotBlank()) {
                        isLoading = true
                        // Make sure to include the country code if the user doesn't
                        val completePhoneNumber = if (phoneNumber.startsWith("+")) phoneNumber else "+91$phoneNumber"
                        viewModel.sendVerificationCode(completePhoneNumber, activity)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = phoneNumber.isNotBlank() && !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Continue", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By Continuing, you agree to our",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Terms of Service & Privacy Policy",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

// ✅ THE FIX: All the code for the carousel is now inside this file.

data class ProductItemAutoScroll(
    val imageRes: Int,
    val name: String
)

@Composable
fun AutoScrollingProductCarousel(navController: NavHostController) {

    val products = listOf(
        ProductItemAutoScroll(R.drawable.milk, "Milk"),
        ProductItemAutoScroll(R.drawable.tea, "Tea"),
        ProductItemAutoScroll(R.drawable.kitkat, "KitKat"),
        ProductItemAutoScroll(R.drawable.choclate, "Chocolate"),
        ProductItemAutoScroll(R.drawable.dryfruits, "Dry Fruits"),
        ProductItemAutoScroll(R.drawable.potato, "Potato"),
        ProductItemAutoScroll(R.drawable.brocli, "Broccoli"),
        ProductItemAutoScroll(R.drawable.fruitsandvegetables, "Fruits & Veg"),
        ProductItemAutoScroll(R.drawable.instantfood, "Instant Food"),
    )

    AutoScrollingHorizontalColumn(
        products = products,
        speed = 1.5f,
        spacing = 8.dp,
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

    LaunchedEffect(Unit) {
        while (true) {
            delay(16) // ~60 FPS
            scrollOffset.floatValue += speed
            if (scrollOffset.floatValue >= (products.size * totalItemWidthPx / itemsPerColumn)) {
                scrollOffset.floatValue = 0f
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth().height(350.dp)) { // Give it a fixed height
        val columnCount = ceil(repeatedProducts.size.toFloat() / itemsPerColumn).toInt()

        for (col in 0 until columnCount) {
            val xOffset = col * totalItemWidthPx - scrollOffset.floatValue
            if (xOffset > -totalItemWidthPx && xOffset < screenWidthPx) {
                Column(
                    modifier = Modifier
                        .graphicsLayer { translationX = xOffset }
                        .width(itemWidth),
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    for (row in 0 until itemsPerColumn) {
                        val index = (col * itemsPerColumn + row) % repeatedProducts.size
                        ProductCardForAutoScroll(product = repeatedProducts[index])
                    }
                }
            }
        }

        Button(
            onClick = {
                // ✅ THE FIX: Navigate to the main graph route, not the home screen directly.
                navController.navigate(Screens.MainGraph.route) {
                    popUpTo(Screens.PhoneAuthScreen.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(vertical = 20.dp, horizontal = 12.dp),
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
        modifier = Modifier.size(100.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.simpleProductColor))
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
