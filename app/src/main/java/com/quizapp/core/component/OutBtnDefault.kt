package com.quizapp.core.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Default OutlinedButton
@Composable
fun OutBtnDefault(modifier: Modifier, onClick: () -> Unit, buttonText: String) {
    OutlinedButton(
        modifier = modifier.size(
            height = TextFieldDefaults.MinHeight + 8.dp,
            width = TextFieldDefaults.MinWidth
        ),
        onClick = onClick,
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.primary)
    ) {
        Text(text = buttonText, color = Color.White)
    }
}