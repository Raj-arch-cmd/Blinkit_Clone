package com.example.blinkit_clone.presentation.screens.CategoryScreen

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import com.example.blinkit_clone.R
import com.example.blinkit_clone.presentation.screens.auth.PhoneAuthViewModel
import kotlinx.coroutines.delay

@Composable
fun PhoneNumberInputScreen(
    viewModel: PhoneAuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var phoneNumber by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as ComponentActivity
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val authState by viewModel.authState.collectAsState()
    LaunchedEffect(authState) {
        isLoading = false
    }

    var canLoadImages by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        // ✅ THE FIX: Wait for activity launch before heavy decodes
        delay(600)
        canLoadImages = true
    }

    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 1. BACKGROUND CAROUSEL
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.55f)
                .alpha(0.6f)
        ) {
            AutoScrollingProductCarousel(canLoadImages = canLoadImages)
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.4f),
                                Color.White
                            )
                        )
                    )
            )
        }

        // ✅ THE SKIP BUTTON: Fixed positioning and visibility
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 12.dp, end = 16.dp), // Increased top spacing to stay clear of status bar icons
            contentAlignment = Alignment.TopEnd
        ) {
            Surface(
                onClick = {
                    Log.d("PhoneNumberInputScreen", "Skip clicked")
                    navController.navigate(Screens.MainGraph.route) {
                        popUpTo(Screens.PhoneAuthScreen.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.85f), // Darker background for high visibility
                contentColor = Color.White,
                tonalElevation = 4.dp
            ) {
                Text(
                    text = "Skip",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // 2. MAIN CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(screenHeight * 0.10f))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically { -40 }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Card(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7CB45))
                    ) {
                        AsyncImage(
                            model = if (canLoadImages) ImageRequest.Builder(context)
                                .data(R.drawable.blinkit_logo)
                                .size(200) // Constrain logo size
                                .precision(Precision.INEXACT)
                                .crossfade(true)
                                .build() else null,
                            contentDescription = "Blinkit Logo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "India's last minute app",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        letterSpacing = (-0.5).sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 3. PHONE INPUT CONTAINER
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White,
                shadowElevation = 24.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Log in or sign up",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { if (it.length <= 10) phoneNumber = it },
                        placeholder = { Text("Enter mobile number", color = Color.Gray) },
                        prefix = { Text("+91 ", fontWeight = FontWeight.Bold, color = Color.Black) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF0E8A44),
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = Color(0xFF0E8A44)
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (phoneNumber.length == 10) {
                                isLoading = true
                                val completePhoneNumber = "+91$phoneNumber"
                                viewModel.sendVerificationCode(completePhoneNumber, activity)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = phoneNumber.length == 10 && !isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0E8A44),
                            disabledContainerColor = Color.LightGray
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                        } else {
                            Text("Continue", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "By continuing, you agree to our Terms & Conditions",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhoneNumberInputScreen() {
    PhoneNumberInputScreen(navController = rememberNavController())
}
