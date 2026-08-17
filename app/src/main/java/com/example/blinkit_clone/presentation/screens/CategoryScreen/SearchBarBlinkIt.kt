package com.example.blinkit_clone.presentation.screens.CategoryScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun BlinkitSearchBar(navController: NavHostController? = null) {
    var searchQuery by remember { mutableStateOf("") }

    TextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .height(54.dp),
        placeholder = { 
            Text(
                "Search \"milk\"", 
                fontSize = 15.sp, 
                color = Color.Gray.copy(alpha = 0.8f)
            ) 
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Black.copy(alpha = 0.7f),
                modifier = Modifier.size(22.dp)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Microphone Icon",
                tint = Color.Black.copy(alpha = 0.7f),
                modifier = Modifier.size(22.dp)
            )
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 15.sp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF3F4F6),
            unfocusedContainerColor = Color(0xFFF3F4F6),
            disabledContainerColor = Color(0xFFF3F4F6),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFF0E8A44)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun BlinkitSearchBarPreview() {
    BlinkitSearchBar()
}
