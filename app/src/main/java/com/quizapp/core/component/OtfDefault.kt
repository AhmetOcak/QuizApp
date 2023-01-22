package com.quizapp.core.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.quizapp.R

// Default OutlinedTextField
@Composable
fun OtfDefault(
    modifier: Modifier,
    value: String = "",
    onValueChanged: (String) -> Unit,
    placeHolderText: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.size(
            height = TextFieldDefaults.MinHeight + 8.dp,
            width = TextFieldDefaults.MinWidth
        ),
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        maxLines = 1,
        keyboardActions = KeyboardActions(onNext = {}),
        placeholder = {
            Text(
                text = placeHolderText
            )
        },
        visualTransformation = if (keyboardType == KeyboardType.Password && !showPassword) PasswordVisualTransformation(
            mask = '*'
        ) else VisualTransformation.None,
        singleLine = true,
        shape = RoundedCornerShape(20),
        trailingIcon = {
            if (keyboardType != KeyboardType.Password) null else {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = painterResource(
                            id = if (showPassword) R.drawable.show_password else R.drawable.hide_password
                        ),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }
    )
}