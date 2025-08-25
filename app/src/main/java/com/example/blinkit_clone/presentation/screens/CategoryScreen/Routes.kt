package com.example.blinkit_clone.presentation.CategoryScreen

sealed class Screens(val route: String) {
    object PhoneAuthScreen : Screens("phone_auth")
    object HomeScreen : Screens("home")
    object CategoryScreen : Screens("category")
    object OrderAgainScreen : Screens("order_again")
    object PrintScreen : Screens("print")
    object SearchBarScreen : Screens("search_screen")
    object ProfileScreen : Screens("profile")
    object ProductScreen : Screens("product_screen")
    object VerticalTabProductsScreen : Screens("vertical_tab_products")
    object FinalCheckOutScreen : Screens("final_checkout_screen")

    // Helper function to get route with arguments if needed
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}