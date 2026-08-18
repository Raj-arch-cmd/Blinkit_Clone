package com.example.blinkit_clone.presentation.screens.CategoryScreen

import android.util.Log
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
import com.example.blinkit_clone.presentation.screens.auth.PhoneAuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlinkitProfileScreen(
    navController: NavHostController,
    listState: androidx.compose.foundation.lazy.grid.LazyGridState,
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
                .verticalScroll(rememberScrollState())
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
                    // ✅ THE FIX: Updated the name and phone number.
                    Text("Raj Singh", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("+91 8957132374", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                }
            }

            // Profile Options
            Column(modifier = Modifier.padding(16.dp)) {
                // ✅ THE FIX: Made all options clickable and navigate to their respective screens.
                ProfileOptionItem(
                    icon = Icons.Default.LocationOn,
                    title = "Addresses",
                    subtitle = "Manage your delivery addresses"
                ) {
                    navController.navigate(Screens.AddressScreen.route)
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ProfileOptionItem(
                    icon = painterResource(R.drawable.heart1),
                    title = "Favorites",
                    subtitle = "Your favorite products"
                ) {
                    navController.navigate(Screens.FavoritesScreen.route)
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ProfileOptionItem(
                    icon = painterResource(R.drawable.order),
                    title = "Orders",
                    subtitle = "View your order history"
                ) {
                    navController.navigate(Screens.OrdersScreen.route)
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ProfileOptionItem(
                    icon = painterResource(R.drawable.wallet),
                    title = "Payments",
                    subtitle = "Payment methods & wallet"
                ) {
                    navController.navigate(Screens.PaymentsScreen.route)
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                ProfileOptionItem(
                    icon = painterResource(R.drawable.logout),
                    title = "Log Out",
                    subtitle = "End your current session"
                ) {
                    Log.d("BlinkitProfileScreen", "Logout clicked")
                    viewModel.signOut()
                }

                // ✅ THE FIX: Increased spacer to 120dp to ensure Logout is clickable above BottomNav
                Spacer(modifier = Modifier.height(120.dp))
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
