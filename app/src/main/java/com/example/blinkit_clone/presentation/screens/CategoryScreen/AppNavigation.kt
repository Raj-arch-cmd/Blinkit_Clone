package com.example.blinkit_clone.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blinkit_clone.presentation.screens.CategoryScreen.Screens
import com.example.blinkit_clone.presentation.screens.auth.PhoneAuthViewModel
import com.example.projectnew.presentation.screens.PhoneAuthScreen

import kotlinx.coroutines.flow.distinctUntilChanged

// ✅ THE FIX: Corrected the import paths to match your project structure.


@Composable
fun AppNavigation(
    viewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val isLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == false) {
            navController.navigate(Screens.PhoneAuthScreen.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
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

        composable(Screens.MainGraph.route) {
            val listState = rememberLazyListState()
            var isVisible by remember { mutableStateOf(true) }
            LaunchedEffect(listState) {
                var lastIndex = 0
                var lastScrollOffset = 0
                snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
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
                viewModel = viewModel
            )
        }
    }
}
