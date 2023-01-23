package com.quizapp.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.quizapp.R

@Composable
fun CircleCheckbox(modifier: Modifier, onChecked: () -> Unit, selected: Boolean = false) {

    val imageVector = if (selected) R.drawable.ic_baseline_check_circle else R.drawable.ic_outline_circle_24
    val tint = if (selected) Color.Blue else Color.White
    val background = if (selected) Color.White else Color.Transparent

    IconButton(onClick = onChecked) {
        Icon(
            modifier = modifier.background(color = background, shape = CircleShape),
            painter = painterResource(id = imageVector),
            contentDescription = null,
            tint = tint
        )
    }
}