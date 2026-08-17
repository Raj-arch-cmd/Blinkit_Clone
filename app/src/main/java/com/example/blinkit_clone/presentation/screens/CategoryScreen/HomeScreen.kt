package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BlinkItTabRow
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BlinkitSearchBar
import com.example.blinkit_clone.presentation.screens.CategoryScreen.Screens
// ✅ THE FIX: The entire app is now controlled by our new AppNavigation.

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    listState: androidx.compose.foundation.lazy.grid.LazyGridState
) {

    val categories = remember {
        listOf(
            BlinkItCategoryData("All", R.drawable.grocerybag),
            BlinkItCategoryData("Summer", R.drawable.sun),
            BlinkItCategoryData("Electronics", R.drawable.headphones),
            BlinkItCategoryData("Beauty", R.drawable.blush),
            BlinkItCategoryData("Kids", R.drawable.bottlebaby),
        )
    }

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val firstVisibleItemScrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val isScrolled by remember { derivedStateOf { firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0 } }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxHeaderHeight = screenHeight * 0.11f

    val headerHeightDp = animateDpAsState(
        targetValue = if (isScrolled) 0.dp else maxHeaderHeight,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "headerHeight"
    )

    val scrollOffset = remember {
        derivedStateOf {
            (firstVisibleItemIndex * 80 + firstVisibleItemScrollOffset).coerceAtMost(80)
        }
    }

    val density = LocalDensity.current

    val categoryBackground = remember(selectedTabIndex) {
        getCategoryGradient(categories[selectedTabIndex])
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(categoryBackground)
                    .padding(horizontal = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .graphicsLayer {
                            translationY = with(density) { -scrollOffset.value.dp.toPx() }
                        }
                        .height(headerHeightDp.value)
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "DELIVERY IN",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black.copy(alpha = 0.6f),
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "10 minutes",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black,
                                lineHeight = 24.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color = Color.Black.copy(alpha = 0.5f))
                                .clickable {
                                    navController.navigate(Screens.ProfileScreen.route)
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = "Biharipura, Vijay Nagar, Bhim Nagar, Vijay",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black.copy(alpha = 0.8f),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                BlinkitSearchBar(navController)
                BlinkItTabRow(
                    selectedIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it },
                    categories = categories
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = selectedTabIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { width -> width } + fadeIn() with
                                slideOutHorizontally { width -> -width } + fadeOut()
                    } else {
                        slideInHorizontally { width -> -width } + fadeIn() with
                                slideOutHorizontally { width -> width } + fadeOut()
                    }
                },
                label = "SlideTabTransition"
            ) { index ->
                when (index) {
                    0 -> AllCategoryScreen(navController, listState = listState)
                    1 -> SummerCategoryScreen(navController, listState = listState)
                    2 -> ElectronicsScreen(navController, listState = listState)
                    3 -> BeautyScreen(navController, listState = listState)
                    4 -> KidsScreen(navController, listState = listState)
                    else -> AllCategoryScreen(navController, listState = listState)
                }
            }
        }
    }
}
