package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkit_clone.R
import com.example.blinkit_clone.presentation.screens.BlinkItCategoryData


@Composable
fun BlinkItTabRow(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    categories: List<BlinkItCategoryData>
) {
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.9f)),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                height = 0.dp // No visible indicator line
            )
        },
        divider = {}
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (selectedIndex == index) {
                            getCategoryGradient(category)
                        } else {
                            Color.Transparent
                        }
                    ),
                content = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = category.icon),
                            contentDescription = category.title,
                            modifier = Modifier.size(24.dp),
                            tint = if (selectedIndex == index) Color.White else Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = category.title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (selectedIndex == index) Color.White else Color.Black,
                            textAlign = TextAlign.Center,
                            // ✅ THE FIX: Ensure text stays on one line and doesn't wrap awkwardly.
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            )
        }
    }
}

// Helper function to get gradient based on category
fun getCategoryGradient(category: BlinkItCategoryData): Color {
    return when (category.title) {
        "Summer" -> Color(0xFFFF9800) // Orange
        "Electronics" -> Color(0xFF2196F3) // Blue
        "Beauty" -> Color(0xFFE91E63) // Pink
        "Kids" -> Color(0xFF4CAF50) // Green
        else -> Color(0xFF6F6A1E) // Default gold
    }
}


// ✅ THE FIX: Added a Preview so you can see the component in the editor.
@Preview(showBackground = true)
@Composable
fun BlinkItTabRowPreview() {
    // Mock data for the preview
    val categories = listOf(
        BlinkItCategoryData("All", R.drawable.grocerybag),
        BlinkItCategoryData("Summer", R.drawable.sun),
        BlinkItCategoryData("Electronics", R.drawable.headphones),
        BlinkItCategoryData("Beauty", R.drawable.blush),
        BlinkItCategoryData("Kids", R.drawable.bottlebaby),
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Display the component in a Box for better preview rendering
    Box(modifier = Modifier.background(Color.LightGray).padding(16.dp)) {
        BlinkItTabRow(
            selectedIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it },
            categories = categories
        )
    }
}
