package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.Profile.AddressScreen
import com.example.blinkit_clone.Profile.FavoritesScreen
import com.example.blinkit_clone.Profile.OrdersScreen
import com.example.blinkit_clone.Profile.PaymentsScreen
import com.example.blinkit_clone.R
import com.example.blinkit_clone.presentation.screens.HomeScreen

import com.example.projectnew.presentation.screens.CategoryScreens.OrderAgainScreen
import com.example.projectnew.presentation.screens.PhoneAuthViewModel

@Composable
fun MainScreen(
    isVisible: Boolean,
    listState: LazyListState,
    viewModel: PhoneAuthViewModel
) {
    val bottomBarNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavigationBar(navController = bottomBarNavController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomBarNavController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screens.HomeScreen.route) {
                HomeScreen(navController = bottomBarNavController, listState = listState)
            }
            composable(Screens.CategoryScreen.route) {
                CategoryScreen(navController = bottomBarNavController, listState = listState)
            }
            composable(Screens.OrderAgainScreen.route) {
                OrderAgainScreen(navController = bottomBarNavController, listState = listState)
            }
            composable(Screens.PrintScreen.route) {
                PrintScreen(navController = bottomBarNavController, listState = listState)
            }
            composable(Screens.ProfileScreen.route) {
                BlinkitProfileScreen(
                    navController = bottomBarNavController,
                    listState = listState,
                    viewModel = viewModel
                )
            }

            // ✅ THE FIX: Replaced placeholders with calls to your new, functional screens.
            composable(Screens.AddressScreen.route) {
                AddressScreen(navController = bottomBarNavController)
            }
            composable(Screens.FavoritesScreen.route) {
                FavoritesScreen(navController = bottomBarNavController)
            }
            composable(Screens.OrdersScreen.route) {
                OrdersScreen(navController = bottomBarNavController)
            }
            composable(Screens.PaymentsScreen.route) {
                PaymentsScreen(navController = bottomBarNavController)
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.OrderAgain,
        BottomNavItem.Print,
        BottomNavItem.Profile
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        modifier = Modifier
            .height(95.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp)),
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(28.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF0E8A44),
                    selectedTextColor = Color(0xFF0E8A44),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFE8F5E9)
                )
            )
        }
    }
}

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    object Home : BottomNavItem("Home", R.drawable.home, Screens.HomeScreen.route)
    object Category : BottomNavItem("Category", R.drawable.category, Screens.CategoryScreen.route)
    object OrderAgain : BottomNavItem("Order Again", R.drawable.order_again, Screens.OrderAgainScreen.route)
    object Print : BottomNavItem("Print", R.drawable.print, Screens.PrintScreen.route)
    object Profile : BottomNavItem("Profile", R.drawable.profile, Screens.ProfileScreen.route)
}


@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    Box(modifier = Modifier.background(Color.LightGray).padding(16.dp)) {
        BottomNavigationBar(navController = navController)
    }
}
