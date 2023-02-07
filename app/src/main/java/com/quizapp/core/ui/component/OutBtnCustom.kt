package com.quizapp.core.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

// Default OutlinedButton
@Composable
fun OutBtnCustom(modifier: Modifier, onClick: () -> Unit, buttonText: String) {
    OutlinedButton(
        modifier = modifier
            .size(
                height = TextFieldDefaults.MinHeight + 8.dp,
                width = TextFieldDefaults.MinWidth
            )
            .bounceClick(),
        onClick = onClick,
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.onPrimary
        )
    ) {
        Text(text = buttonText, color = MaterialTheme.colors.primary)
    }
}

enum class ButtonState { Pressed, Idle }

private fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.70f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}