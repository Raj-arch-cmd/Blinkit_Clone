package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.blinkit_clone.Utills.CartViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    listState: androidx.compose.foundation.lazy.grid.LazyGridState,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.PhoneAuthScreen.route
    ) {
        // Auth Flow
        composable(Screens.PhoneAuthScreen.route) {
            PhoneAuthScreen(navController = navController)
        }

        // Main app screens - Only pass listState to screens that need it
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController = navController, listState = listState)
        }

        composable(Screens.OrderAgainScreen.route) {
            OrderAgainScreen(navController = navController, listState = listState , cartViewModel = cartViewModel)
        }

        composable(Screens.CategoryScreen.route) {
            CategoryScreen(navController = navController, listState = listState)
        }

        composable(Screens.PrintScreen.route) {
            PrintScreen(navController = navController ,listState=listState)
        }

        composable(Screens.SearchBarScreen.route) {
            SearchBarScreen(navController = navController, cartViewModel = cartViewModel)
        }

        composable(Screens.ProfileScreen.route) {
            BlinkitProfileScreen(navController = navController, listState =listState)
        }

        composable(Screens.ProductScreen.route) {
            ProductScreen(navController = navController, cartViewModel = cartViewModel)
        }

        composable(Screens.VerticalTabProductsScreen.route) {
            VerticalTabProductsScreen(navController = navController, cartViewModel = cartViewModel)
        }

        composable(Screens.FinalCheckOutScreen.route) {
            FinalCheckOutScreen(navController = navController, cartViewModel = cartViewModel)
        }
    }
}
