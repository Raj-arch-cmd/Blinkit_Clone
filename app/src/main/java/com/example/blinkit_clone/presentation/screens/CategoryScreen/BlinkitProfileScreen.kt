package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blinkit_clone.R
import com.example.projectnew.presentation.screens.PhoneAuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlinkitProfileScreen(
    navController: NavHostController,
    listState: LazyListState,
    viewModel: PhoneAuthViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()) // Make the column scrollable
        ) {
            // Profile Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.LightGray, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(60.dp)
                                .align(Alignment.Center),
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("John Doe", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("+91 9876543210", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                }
            }

            // Profile Options
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileOptionItem(icon = Icons.Default.LocationOn, title = "Addresses", subtitle = "Manage your delivery addresses") {}
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ProfileOptionItem(icon = painterResource(R.drawable.heart1), title = "Favorites", subtitle = "Your favorite products") {}
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ProfileOptionItem(icon = painterResource(R.drawable.order), title = "Orders", subtitle = "View your order history") {}
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ProfileOptionItem(icon = painterResource(R.drawable.wallet), title = "Payments", subtitle = "Payment methods & wallet") {}
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // ✅ THE FIX: Added the Log Out item to the list.
                ProfileOptionItem(
                    icon = painterResource(R.drawable.logout),
                    title = "Log Out",
                    subtitle = "End your current session"
                ) {
                    viewModel.signOut() // This will log the user out
                }
            }
        }
    }
}

@Composable
fun ProfileOptionItem(
    icon: Any,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (icon) {
            is androidx.compose.ui.graphics.painter.Painter -> {
                Icon(
                    painter = icon,
                    contentDescription = title,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            is androidx.compose.ui.graphics.vector.ImageVector -> {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painterResource(R.drawable.arrowright),
            contentDescription = "Navigate",
            modifier = Modifier.size(16.dp),
            tint = Color.Gray
        )
    }
}
