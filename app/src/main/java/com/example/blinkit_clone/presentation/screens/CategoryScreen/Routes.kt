package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class BlinkItCategoryData(
    val title: String,
    @DrawableRes val icon: Int
)

sealed class Screens(val route: String) {
    object MainGraph : Screens("main_graph")
    object PhoneAuthScreen : Screens("phone_auth")
    object HomeScreen : Screens("home")
    object CategoryScreen : Screens("category")
    object OrderAgainScreen : Screens("order_again")
    object PrintScreen : Screens("print")
    object ProfileScreen : Screens("profile")
    object AddressScreen : Screens("address")
    object FavoritesScreen : Screens("favorites")
    object PaymentsScreen : Screens("payments")
    object OrdersScreen : Screens("orders")
    object CartScreen : Screens("cart")
    object ProductScreen : Screens("product")
    object VerticalTabProductsScreen : Screens("vertical_tab_products")
    object FinalCheckOutScreen : Screens("final_checkout")
    object SearchBarScreen : Screens("search")

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
