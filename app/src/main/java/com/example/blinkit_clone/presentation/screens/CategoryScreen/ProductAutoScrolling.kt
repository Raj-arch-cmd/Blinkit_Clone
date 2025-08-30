package com.example.blinkit_clone.presentation.screens.CategoryScreen

// ... (keep all the other code in this file the same) ...

sealed class Screens(val route: String) {
    object MainGraph : Screens("main_graph")
    object HomeScreen : Screens("HomeScreen")
    object PhoneAuthScreen : Screens("PhoneAuthScreen")
    object OrderAgainScreen : Screens("OrderAgainScreen")
    object CategoryScreen : Screens("CategoryScreen")
    object PrintScreen : Screens("PrintScreen")
    object SearchBarScreen : Screens("SearchBarScreen")
    object ProfileScreen : Screens("ProfileScreen")
    object ProductScreen : Screens("ProductScreen")
    object VerticalTabProductsScreen : Screens("VerticalTabProductsScreen")
    object FinalCheckOutScreen : Screens("FinalCheckOutScreen")

    // ✅ THE FIX: Added new routes for the profile section screens.
    object AddressScreen : Screens("address_screen")
    object FavoritesScreen : Screens("favorites_screen")
    object OrdersScreen : Screens("orders_screen")
    object PaymentsScreen : Screens("payments_screen")
}

// ... (keep all the other code in this file the same) ...
