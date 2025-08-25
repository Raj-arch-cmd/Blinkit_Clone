package com.example.blinkit_clone.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.blinkit_clone.R

// Custom Colors defined in the file
val BlinkitLightYellow = Color(0xFFFFFEBE6)
val BlinkitLightGreen = Color(0xFFFBF8E9)
val BlinkitDarkGray = Color(0xFFF57575)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlinkitProfileScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            HorizontalDivider(color = Color.Gray, thickness = 0.2.dp)
            // Profile Info Section (non-scrolling)
            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "Your account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.telephone),
                        contentDescription = null
                    )
                    Text(
                        text = "904197730",
                        fontSize = 16.sp,
                        color = BlinkitDarkGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }

            // Main Scrollable Content
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                // Quick Actions Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = BlinkitLightYellow),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        QuickActionItem(text = "Zomato Money", image = R.drawable.payment)
                        QuickActionItem(text = "Support", image = R.drawable.message)
                        QuickActionItem(text = "Payments", image = R.drawable.payment)
                    }
                }

                // Appearance Section Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BlinkitLightGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .background(Color.White, shape = CircleShape)
                                    .size(30.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.sun),
                                    contentDescription = "Appearance",
                                    tint = BlinkitDarkGray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Appearance",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "LIGHT",
                                fontSize = 14.sp,
                                color = BlinkitDarkGray
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Select",
                                tint = BlinkitDarkGray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Your Information
                SectionHeader(title = "YOUR INFORMATION")
                SettingsItem(title = "Your orders", image = R.drawable.order)
                SettingsItem(title = "Bookmarked recipes", image = R.drawable.thumbs)
                SettingsItem(title = "Address book", image = R.drawable.addressbook)
                SettingsItem(title = "GST details", image = R.drawable.reciept)
                SettingsItem(title = "E-Gift cards", image = R.drawable.giftcard)

                // Payments and Coupons
                SectionHeader(title = "PAYMENTS AND COUPONS")
                SettingsItem(title = "Wallet", image = R.drawable.payment)
                SettingsItem(title = "Zomato Money", image = R.drawable.payment)
                SettingsItem(title = "Payment Settings", image = R.drawable.payment)
                SettingsItem(title = "Collected coupons", image = R.drawable.coupons)

                // Here is the Other Information Cards
                SectionHeader(title = "OTHER INFORMATION")
                SettingsItem(title = "Share the app", image = R.drawable.faltuh)
                SettingsItem(title = "About us", image = R.drawable.info)
                SettingsItem(title = "Get Feeding India receipt", image = R.drawable.eye)
                SettingsItem(title = "Account privacy", image = R.drawable.lock)
                SettingsItem(title = "Notification preferences", image = R.drawable.notification)
                SettingsItem(title = "Log out", image = R.drawable.logout)

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
fun QuickActionItem(text: String, image: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(image),
            contentDescription = text,
            tint = Color.DarkGray,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        color = BlinkitDarkGray,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
    )
}

@Composable
fun SettingsItem(title: String, image: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color = Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(image),
                contentDescription = title,
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = BlinkitDarkGray,
            modifier = Modifier.size(20.dp)
        )
    }
}