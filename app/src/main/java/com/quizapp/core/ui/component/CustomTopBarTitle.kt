package com.quizapp.core.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomTopBarTitle(modifier: Modifier, title: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = title,
        style = MaterialTheme.typography.h1.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.primaryVariant
        ),
        textAlign = TextAlign.Center
    )
}