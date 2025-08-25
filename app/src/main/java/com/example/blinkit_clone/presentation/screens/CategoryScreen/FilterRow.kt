package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.res.colorResource // Replaced for preview compatibility
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkit_clone.R


// Ensure this R file path is correct for your project

@Composable
fun FiltersRow(filters: List<String>) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyRow(
            modifier = Modifier.weight(2f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(filters) { filter ->
                FilterChip(
                    onClick = {
                        selectedFilter = if (selectedFilter == filter) null else filter
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.White,
                        labelColor = Color.DarkGray,
                        selectedContainerColor = Color.LightGray // Good to specify selected color
                    ),
                    selected = selectedFilter == filter,
                    leadingIcon = {
                        // REFACTORED: Use a 'when' statement for cleaner logic
                        val iconRes = when (filter) {
                            "Tomato", "Apple", "Kiwi", "Vegetables" -> R.drawable.milk // Placeholder
                            else -> null // No icon for other filters
                        }
                        if (iconRes != null) {
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = "$filter icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    },
                    trailingIcon = {
                        // REFACTORED: Simplified logic for trailing icons
                        when {
                            filter == "Filter" -> {
                                Icon(
                                    painter = painterResource(R.drawable.arrowdown),
                                    contentDescription = "Dropdown arrow",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            selectedFilter == filter -> {
                                Icon(
                                    painter = painterResource(R.drawable.close),
                                    contentDescription = "Clear filter",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    },
                    label = { Text(filter, fontSize = 12.sp) },
                    enabled = true,
                    // FIXED: Use a direct Color for preview compatibility.
                    // Your app will still work correctly when run on a device.
                    border = BorderStroke(0.4.dp, color = Color.LightGray)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FiltersRowPreview() {
    FiltersRow(
        filters = listOf("Filter", "Tomato", "Apple", "Kiwi", "Vegetables", "Dairy", "Meat")
    )
}