package com.example.blinkit_clone.presentation.screens.CategoryScreen


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.blinkit_clone.presentation.CategoryScreen.AllCategoryScreen
import com.example.blinkit_clone.R


@Composable
fun CategoryScreen(navController: NavHostController,listState: LazyListState){

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
    val headerHeightDp = animateDpAsState(
        targetValue = if (isScrolled) 0.dp else 88.dp,
        animationSpec = tween(durationMillis = 500,easing = FastOutSlowInEasing),
        label = "headerHeight"
    )

    val scrollOffset = remember {
        derivedStateOf {
            (firstVisibleItemIndex * 80 + firstVisibleItemScrollOffset).coerceAtMost(80)
        } }

    val topContentOffset by animateDpAsState(
        targetValue = -scrollOffset.value.dp,
        animationSpec = tween(durationMillis = 500,easing = FastOutSlowInEasing),
        label = "topContentOffset"
    )

    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1C99AC), // Zepto's brand purple (primary)
                            Color(0xFF54C5D5)  // Secondary purple
                        )
                    ))
                    .padding(horizontal = 6.dp).shadow(elevation = 0.dp)
            ) {
                // 🔸 SLIDING SECTION (Delivery info + location)
                Column(
                    modifier = Modifier
                        .offset(y = topContentOffset).height(headerHeightDp.value)
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
                                text = "Grocery in",
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
                            modifier = Modifier.padding(top = 8.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color = Color.Black.copy(alpha = 0.5f)).clickable {
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
                            text = "Biharipura, Vijay Nagar, Bhim Nagar, Vijay",
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
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                AllCategoryScreen(navController)
            }
        }
    }
}