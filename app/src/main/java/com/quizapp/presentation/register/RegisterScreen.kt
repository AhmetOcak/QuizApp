package com.quizapp.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quizapp.core.component.OtfDefault
import com.quizapp.core.component.OutBtnDefault

@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {

    RegisterScreenContent(modifier = modifier)
}

@Composable
private fun RegisterScreenContent(modifier: Modifier) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TitleSection(modifier = modifier)
            InputSection(modifier = modifier)
            RegisterButton(modifier = modifier)
        }
    }
}

@Composable
private fun TitleSection(modifier: Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        text = "JOIN US",
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Start
    )
}

@Composable
private fun InputSection(modifier: Modifier) {
    OtfDefault(
        modifier = modifier.fillMaxWidth(),
        onValueChanged = {},
        placeHolderText = "Username"
    )
    OtfDefault(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        onValueChanged = {},
        placeHolderText = "Email"
    )
    OtfDefault(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        onValueChanged = {},
        placeHolderText = "Password",
        keyboardType = KeyboardType.Password
    )
    OtfDefault(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        onValueChanged = {},
        placeHolderText = "Confirm Password",
        keyboardType = KeyboardType.Password
    )
}

@Composable
private fun RegisterButton(modifier: Modifier) {
    OutBtnDefault(
        modifier = modifier.fillMaxWidth().padding(top = 32.dp),
        onClick = { /*TODO*/ },
        buttonText = "Register"
    )
}