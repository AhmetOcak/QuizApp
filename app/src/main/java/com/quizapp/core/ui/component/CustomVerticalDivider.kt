package com.quizapp.core.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomVerticalDivider(color: Color = MaterialTheme.colors.primaryVariant) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 8.dp)
    ) {
        Divider(
            color = color,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
    }
}