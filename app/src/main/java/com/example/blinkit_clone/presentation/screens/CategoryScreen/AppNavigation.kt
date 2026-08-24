package com.example.blinkit_clone.presentation.screens.CategoryScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.presentation.screens.auth.PhoneAuthViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun AppNavigation(
    viewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val isLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    // ✅ THE FIX: Handle explicit logout via one-shot event
    LaunchedEffect(Unit) {
        viewModel.logoutEvent.collect {
            Log.d("AppNavigation", "NAVIGATE_TO_LOGIN (via logout event)")
            navController.navigate(Screens.PhoneAuthScreen.route) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    // Handle initial state and Skip behavior
    LaunchedEffect(isLoggedIn) {
        Log.d("AppNavigation", "isLoggedIn state changed: $isLoggedIn")
        // We only redirect automatically if the state BECOMES false while not on Auth screen
        // But for Skip users, it's already false. So we rely on startDestination.
    }

    if (isLoggedIn == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn == true) Screens.MainGraph.route else Screens.PhoneAuthScreen.route
    ) {
        composable(Screens.PhoneAuthScreen.route) {
            PhoneAuthScreen(navController = navController)
        }

        // Register ProductScreen at top-level too for robustness
        composable(Screens.ProductScreen.route) {
            ProductScreen(navController = navController)
        }

        composable(Screens.MainGraph.route) {
            val listState = androidx.compose.foundation.lazy.grid.rememberLazyGridState()
            var isVisible by remember { mutableStateOf(true) }
            
            // ✅ THE FIX: Defer heavy MainScreen image loading to keep navigation smooth
            var canLoadImages by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(600)
                canLoadImages = true
            }

            LaunchedEffect(listState) {
                var lastIndex = 0
                var lastScrollOffset = 0
                androidx.compose.runtime.snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
                    .distinctUntilChanged()
                    .collect { (index, scrollOffset) ->
                        isVisible = if (index > lastIndex || (scrollOffset > lastScrollOffset + 50)) {
                            false
                        } else if (index < lastIndex || (scrollOffset < lastScrollOffset - 50)) {
                            true
                        } else {
                            isVisible
                        }
                        lastIndex = index
                        lastScrollOffset = scrollOffset
                    }
            }

            MainScreen(
                isVisible = isVisible,
                listState = listState,
                viewModel = viewModel,
                canLoadImages = canLoadImages
            )
        }
    }
}
