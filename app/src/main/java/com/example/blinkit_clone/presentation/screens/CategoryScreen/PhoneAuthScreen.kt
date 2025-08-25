package com.example.projectnew.presentation.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.Common.AuthState
import com.example.blinkit_clone.presentation.screens.CategoryScreen.Screens
import com.example.blinkit_clone.presentation.screens.OtpVerificationScreen
import com.example.blinkit_clone.presentation.screens.PhoneNumberInputScreen

@Composable
fun PhoneAuthScreen(
    viewModel: PhoneAuthViewModel = hiltViewModel(),
    navController: NavHostController
    // ✅ THE FIX: Removed the LazyListState parameter from here as well.
) {
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_LONG).show()
            }
            is AuthState.Verified -> {
                Toast.makeText(context, "Authentication successful!", Toast.LENGTH_LONG).show()
                // Navigate to the main app graph and clear the login screen from history.
                navController.navigate(Screens.MainGraph.route) {
                    popUpTo(Screens.PhoneAuthScreen.route) {
                        inclusive = true
                    }
                }
            }
            else -> {}
        }
    }

    when (authState) {
        is AuthState.CodeSent -> OtpVerificationScreen(viewModel)
        else -> PhoneNumberInputScreen(viewModel, navController)
    }
}
