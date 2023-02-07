package com.quizapp.core.ui.component

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CustomLoadingSpinner() {
    CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
}