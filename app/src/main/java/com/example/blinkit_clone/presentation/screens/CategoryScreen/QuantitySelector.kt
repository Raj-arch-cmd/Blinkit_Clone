package com.example.blinkit_clone.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blinkit_clone.R

@Composable
fun QuantitySelector(
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color(0xFF0E8A44),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp) // Internal padding
    ) {
        Icon(
            painter = painterResource(id = R.drawable.minus),
            contentDescription = "Remove",
            modifier = Modifier
                .size(24.dp)
                .clickable { onRemove() }
                .padding(4.dp),
            tint = Color(0xFF0E8A44)
        )
        Text(
            text = quantity.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF0E8A44),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "Add",
            modifier = Modifier
                .size(24.dp)
                .clickable { onAdd() }
                .padding(4.dp),
            tint = Color(0xFF0E8A44)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuantitySelectorPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        QuantitySelector(
            quantity = 1,
            onAdd = { },
            onRemove = { }
        )
    }
}

