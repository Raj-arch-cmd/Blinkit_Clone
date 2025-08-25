package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

import com.example.blinkit_clone.R

@Composable
fun PrintScreen(navController: NavHostController, listState: LazyListState) {

    // Derive scroll value from LazyListState
    val firstVisibleItemScrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }

    // Calculate scroll position for animations
    val isScrolled by remember {
        derivedStateOf {
            firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0
        }
    }

    // Convert scroll offset to dp for animations
    val headerHeightdp by animateDpAsState(
        targetValue = if (isScrolled) 0.dp else 88.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "headerHeight"
    )

    val scrollOffset = remember {
        derivedStateOf {
            (firstVisibleItemIndex * 80 + firstVisibleItemScrollOffset).coerceAtMost(maximumValue = 80)
        }
    }

    val topContentOffset by animateDpAsState(
        targetValue = -scrollOffset.value.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "topContentOffset"
    )

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFB61E), // Darker gold
                                Color(0xFFFADC64)  // Medium amber gold
                            )
                        )
                    )
                    .padding(horizontal = 6.dp)
                    .shadow(elevation = 8.dp)
            ) {
                // SLIDING SECTION (Delivery Info + location)
                Column(
                    modifier = Modifier
                        .offset(y = topContentOffset)
                        .height(headerHeightdp)
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    // Delivery Time Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Grocery in,",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "10 minutes",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        // Profile Icon
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color = Color.Black.copy(alpha = 0.5f))
                                .clickable {
                                    //Navigate to profile screen
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color.White
                            )
                        }
                    }

                    // Location Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = "Biharinpura, Vijay Nagar, Bhim Nagar, Vijay",
                            fontSize = 14.sp,
                            color = Color.Black.copy(alpha = 0.8f),
                            maxLines = 1
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand",
                            tint = Color.Black
                        )
                    }
                }
                BlinkitSearchBar(navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFFFFF8E9))
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    PrintScreenText()
                    Spacer(modifier = Modifier.height(8.dp))
                    PrintingServiceCards()
                    PrintRentReceiptScreen()
                    PrintGuidelinesImage()
                }
            }
        }
    }
}

// NOTE: The following are placeholder functions as their definitions
// were not provided in the images.

@Composable
fun PrintScreenText() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Print Store",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Blinkit ensures secure prints at every stage",
            fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray
        )
    }
}




@Composable
fun PrintingServiceCards() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Documents Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Documents",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    FeatureItem(text = "Price starting at ₹3/page")
                    FeatureItem(text = "Paper quality: 70 GSM")
                    FeatureItem(text = "Single side prints")
                    Button(
                        onClick = { /* Handle upload action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Upload Files", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier.padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.form),
                        contentDescription = "Document icon",
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

        // Passport Photos Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                // Features list
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Passport Photos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    FeatureItem(text = "Print on Kodak glossy paper")
                    FeatureItem(text = "Photo sets available: 8, 16, 32")
                    FeatureItem(text = "Paper quality 210 GSM")
                    Button(
                        onClick = { /* Handle upload action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Upload Photos", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier.padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.passport),
                        contentDescription = "Document icon",
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}


@Composable
fun FeatureItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "•",
            color = Color.Gray,
            fontSize = 12.sp
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}




@Composable
fun PrintRentReceiptScreen() {
    Column {
        Text(
            text = "Print your Last Minute Needs",
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Receipt Card
        Card(
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp)
                .height(180.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box {
                // Image
                Image(
                    painter = painterResource(id = R.drawable.rentform),
                    contentDescription = "Rent Receipt",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                // Print Button
                Button(
                    onClick = { /* TODO: Handle print */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF4CAF50)),
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = "Print",
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            // Page count
            Text(
                text = "15 pages",
                color = Color(0xFF1E3A8A),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .background(Color(0xFFDDE7FF), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            // Title
            Text(
                text = "Print your Rent",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Receipt",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Rating Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(times = 4) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "(95)",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Column {
                Text(
                    text = "60% OFF",
                    color = Color(0xFF1E88E5),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "₹30",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "MRP ₹95",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
            }
        }
    }
}

@Composable
fun PrintGuidelinesImage() {
    Image(
        painter = painterResource(id = R.drawable.fastdelivery),
        contentDescription = "Print Guidelines",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentScale = ContentScale.FillWidth
    )
    DoorStepImage()
}

@Composable
fun DoorStepImage() {
    Image(
        painter = painterResource(id = R.drawable.doorstep),
        contentDescription = "Print Guidelines",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentScale = ContentScale.FillWidth
    )
}