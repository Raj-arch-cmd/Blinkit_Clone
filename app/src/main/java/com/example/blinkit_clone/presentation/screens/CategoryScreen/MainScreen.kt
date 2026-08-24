package com.example.blinkit_clone.presentation.screens.CategoryScreen

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
import androidx.navigation.compose.*
import com.example.blinkit_clone.R
import com.example.blinkit_clone.Utills.CartViewModel
import com.example.blinkit_clone.presentation.screens.CategoryScreen.*
import com.example.blinkit_clone.presentation.screens.auth.PhoneAuthViewModel
import com.example.blinkit_clone.presentation.screens.cart.CartScreen
import com.example.blinkit_clone.presentation.screens.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    isVisible: Boolean,
    listState: androidx.compose.foundation.lazy.grid.LazyGridState,
    viewModel: PhoneAuthViewModel,
    cartViewModel: CartViewModel = hiltViewModel(),
    canLoadImages: Boolean = true
) {
    val bottomBarNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            // ✅ THE FIX: Isolated isVisible to this wrapper to prevent MainScreen recomposition
            BottomBarWrapper(
                isVisible = isVisible,
                navController = bottomBarNavController
            )
        },
        floatingActionButton = {
            // ✅ THE FIX: Isolated cartState collection to this wrapper
            CartFabWrapper(
                cartViewModel = cartViewModel,
                navController = bottomBarNavController
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        NavHost(
            navController = bottomBarNavController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screens.HomeScreen.route) {
                HomeScreen(navController = bottomBarNavController, listState = listState, canLoadImages = canLoadImages)
            }
            composable(Screens.CategoryScreen.route) {
                CategoryScreen(navController = bottomBarNavController, listState = listState, canLoadImages = canLoadImages)
            }
            composable(Screens.OrderAgainScreen.route) {
                OrderAgainScreen(
                    navController = bottomBarNavController,
                    listState = listState,
                    cartViewModel = cartViewModel,
                    canLoadImages = canLoadImages
                )
            }
            composable(Screens.PrintScreen.route) {
                PrintScreen(navController = bottomBarNavController)
            }
            composable(Screens.ProfileScreen.route) {
                BlinkitProfileScreen(
                    navController = bottomBarNavController,
                    listState = listState,
                    viewModel = viewModel
                )
            }
            composable(Screens.AddressScreen.route) { AddressScreen(navController = bottomBarNavController) }
            composable(Screens.FavoritesScreen.route) {
                FavoritesScreen(
                    navController = bottomBarNavController,
                    cartViewModel = cartViewModel
                )
            }
            composable(Screens.PaymentsScreen.route) { PaymentsScreen(navController = bottomBarNavController) }
            composable(Screens.OrdersScreen.route) { OrdersScreen(navController = bottomBarNavController) }
            composable(Screens.CartScreen.route) {
                CartScreen(
                    navController = bottomBarNavController,
                    cartViewModel = cartViewModel
                )
            }
            composable(Screens.ProductScreen.route) {
                ProductScreen(navController = bottomBarNavController, cartViewModel = cartViewModel, canLoadImages = canLoadImages)
            }
            composable(Screens.VerticalTabProductsScreen.route) {
                VerticalTabProductsScreen(navController = bottomBarNavController, cartViewModel = cartViewModel)
            }
            composable(Screens.SearchBarScreen.route) {
                SearchBarScreen(navController = bottomBarNavController, cartViewModel = cartViewModel)
            }
            composable(Screens.FinalCheckOutScreen.route) {
                FinalCheckOutScreen(navController = bottomBarNavController, cartViewModel = cartViewModel)
            }
        }
    }
}

@Composable
fun BottomBarWrapper(
    isVisible: Boolean,
    navController: NavHostController
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomNavigationBar(navController = navController)
    }
}

@Composable
fun CartFabWrapper(
    cartViewModel: CartViewModel,
    navController: NavHostController
) {
    val totalItems by cartViewModel.totalItemCount.collectAsState(initial = 0)
    val totalPrice by cartViewModel.totalPrice.collectAsState(initial = 0.0)

    AnimatedVisibility(
        visible = totalItems > 0,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        FloatingActionButton(
            onClick = { navController.navigate(Screens.CartScreen.route) },
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
            .height(72.dp),
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = isSelected,
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
                    indicatorColor = Color(0xFFC8E6C9).copy(alpha = 0.3f)
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
