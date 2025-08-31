package com.example.blinkit_clone.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.R
import com.example.blinkit_clone.Utills.CartViewModel

// ✅ THE FIX: Corrected all import paths to match your final project structure.
import com.example.blinkit_clone.presentation.screens.CategoryScreen.*
import com.example.blinkit_clone.presentation.screens.CategoryScreens.OrderAgainScreen
import com.example.blinkit_clone.presentation.screens.cart.CartScreen
import com.example.blinkit_clone.ui.theme.PhoneAuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    isVisible: Boolean,
    listState: LazyListState,
    viewModel: PhoneAuthViewModel,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val bottomBarNavController = rememberNavController()
    val totalItems by cartViewModel.totalItemCount.collectAsState(initial = 0)
    val totalPrice by cartViewModel.totalPrice.collectAsState(initial = 0.0)

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavigationBar(navController = bottomBarNavController)
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = totalItems > 0,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                FloatingActionButton(
                    onClick = { bottomBarNavController.navigate(Screens.CartScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    containerColor = Color(0xFF0E8A44)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$totalItems item${if (totalItems > 1) "s" else ""} | ${String.format("₹%.2f", totalPrice)}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "View Cart",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
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
                OrderAgainScreen(
                    navController = bottomBarNavController,
                    listState = listState,
                    cartViewModel = cartViewModel
                )
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
            composable(Screens.AddressScreen.route) { AddressScreen(navController = bottomBarNavController) }
            composable(Screens.FavoritesScreen.route) { FavoritesScreen(navController = bottomBarNavController) }
            composable(Screens.PaymentsScreen.route) { PaymentsScreen(navController = bottomBarNavController) }
            composable(Screens.OrdersScreen.route) { OrdersScreen(navController = bottomBarNavController) }
            composable(Screens.CartScreen.route) {
                CartScreen(
                    navController = bottomBarNavController,
                    cartViewModel = cartViewModel
                )
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF0E8A44),
                    selectedTextColor = Color(0xFF0E8A44),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFC8E6C9).copy(alpha = 0.5f)
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
    BottomNavigationBar(navController = navController)
}

