package com.example.blinkit_clone.presentation.screens


import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.presentation.screens.CategoryScreen.AutoScrollingProductCarousel

import com.example.blinkit_clone.R
import com.example.projectnew.presentation.screens.PhoneAuthViewModel


@Composable
fun PhoneNumberInputScreen(viewModel: PhoneAuthViewModel = hiltViewModel(), navController: NavHostController) {
    var phoneNumber by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as ComponentActivity

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // This is our auto-scrolling product carousel
            AutoScrollingProductCarousel(navController = navController)

            Spacer(modifier = Modifier.height(40.dp))

            // Blinkit Logo
            Box(
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFFF200)) // Note: Color value seems incorrect, transcribing as is.
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Link",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Text(
                        text = "it",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.finalScreenBackground)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "India's last minute app",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Enter your phone number",
                fontSize = 16.sp
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number (with country code)") },
                placeholder = { Text("+1 234 567 8901") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (phoneNumber.isNotBlank()) {
                        isLoading = true
                        viewModel.sendVerificationCode(phoneNumber, activity)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = phoneNumber.isNotBlank() && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Send Verification Code")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By Continuing, you agree to our",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Terms of Services Privacy Policy Content Policy",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}