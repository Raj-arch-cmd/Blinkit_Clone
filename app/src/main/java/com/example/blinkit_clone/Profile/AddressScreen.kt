package com.example.blinkit_clone.Profile



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    navController: NavHostController,
    // ✅ THE FIX: Inject the ViewModel to manage the screen's state.
    viewModel: AddressViewModel = hiltViewModel()
) {
    // ✅ THE FIX: Collect the address data from the ViewModel's state flows.
    val fullName by viewModel.fullName.collectAsState()
    val streetAddress by viewModel.streetAddress.collectAsState()
    val city by viewModel.city.collectAsState()
    val state by viewModel.state.collectAsState()
    val zipCode by viewModel.zipCode.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Address") },
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
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ✅ THE FIX: Each text field now updates the ViewModel when its value changes.
            OutlinedTextField(
                value = fullName,
                onValueChange = { viewModel.onFullNameChange(it) },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = streetAddress,
                onValueChange = { viewModel.onStreetAddressChange(it) },
                label = { Text("Street Address / House No.") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = city,
                onValueChange = { viewModel.onCityChange(it) },
                label = { Text("City") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state,
                onValueChange = { viewModel.onStateChange(it) },
                label = { Text("State / Province") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = zipCode,
                onValueChange = { viewModel.onZipCodeChange(it) },
                label = { Text("Zip / Postal Code") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { viewModel.onPhoneNumberChange(it) },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    viewModel.saveAddress()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Save Address", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddressScreenPreview() {
    AddressScreen(navController = rememberNavController())
}
