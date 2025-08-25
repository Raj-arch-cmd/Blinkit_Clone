package com.example.blinkit_clone.presentation.screens



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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import com.example.blinkit_clone.presentation.CategoryScreen.AllCategoryScreen
import com.example.blinkit_clone.presentation.CategoryScreen.SummerCategoryScreen
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BlinkItTabRow
import com.example.blinkit_clone.presentation.screens.CategoryScreen.BlinkitSearchBar
import com.example.blinkit_clone.presentation.screens.CategoryScreen.Screens
import com.example.blinkit_clone.presentation.screens.CategoryScreen.getCategoryGradient
import com.example.blinkit_clone.R
import androidx.annotation.DrawableRes

data class BlinkItCategoryData(
    val title: String,
    @DrawableRes val icon: Int
)



@OptIn(ExperimentalAnimationApi::class
)
@Composable
fun HomeScreen(navController: NavHostController,
               listState: LazyListState){

    val categories = listOf(
        BlinkItCategoryData("All", R.drawable.grocerybag),
        BlinkItCategoryData("Summer", R.drawable.sun),
        BlinkItCategoryData("Electronics", R.drawable.headphones),
        BlinkItCategoryData("Beauty", R.drawable.blush),
        BlinkItCategoryData("Kids", R.drawable.bottlebaby),
    )

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    // Derive scroll value from LazyListState
    val firstVisibleItemScrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }

    // Calculate scroll position for animations
    val isScrolled by remember {
        derivedStateOf {
            firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0
        }
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxHeaderHeight = screenHeight * 0.11f // ~13% of screen height (tweak as needed)

    // Convert scroll offset to dp for animations
    val headerHeightDp = animateDpAsState(
        targetValue = if (isScrolled) 0.dp else maxHeaderHeight,
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

    // Calculate the category background only when selected category changes
    val categoryBackground = remember(selectedTabIndex) {
        getCategoryGradient(categories[selectedTabIndex])
    }



    Scaffold(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(categoryBackground)
                    .padding(horizontal = 6.dp)
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
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        // Profile Icon
                        Box(
                            modifier = Modifier.padding(top = 8.dp)
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
                BlinkItTabRow(
                    selectedIndex = selectedTabIndex,
                    onTabSelected = {selectedTabIndex = it},
                    categories = categories
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AnimatedContent(
                    targetState = selectedTabIndex,
                    transitionSpec = {
                        if (targetState > initialState) {
                            // Forward direction (right)
                            slideInHorizontally { width -> width } + fadeIn() with
                                    slideOutHorizontally { width -> -width } + fadeOut()
                        } else {
                            // Reverse direction (left)
                            slideInHorizontally { width -> -width } + fadeIn() with
                                    slideOutHorizontally { width -> width } + fadeOut()
                        }
                    },
                    label = "SlideTabTransition"
                ) { index ->
                    when (index) {
                        0 -> AllCategoryScreen(navController)
                        1 -> SummerCategoryScreen(navController)
                        else -> AllCategoryScreen(navController)
                    }
                }
            }
        }
    }
}
