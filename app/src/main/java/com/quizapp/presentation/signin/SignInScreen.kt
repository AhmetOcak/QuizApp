package com.quizapp.presentation.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quizapp.core.component.OtfDefault
import com.quizapp.core.component.OutBtnDefault

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {

    SignInScreenContent(modifier = modifier)
}

@Composable
private fun SignInScreenContent(modifier: Modifier) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TitleSection(modifier = modifier)
            SignInSection(modifier = modifier)
            SignInButton(modifier = modifier)
            RegisterNow(modifier = modifier)
        }
    }
}

@Composable
private fun TitleSection(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello Again!",
            style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(
            modifier = modifier.padding(top = 16.dp, start = 54.dp, end = 54.dp),
            text = "Welcome back you've been missed",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SignInSection(modifier: Modifier) {
    SignInInput(modifier = modifier)
    ForgotPassword(modifier = modifier)
}

@Composable
private fun SignInInput(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OtfDefault(
            modifier = modifier.fillMaxWidth(),
            onValueChanged = {},
            placeHolderText = "Enter email",
            keyboardType = KeyboardType.Email
        )
        OtfDefault(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = {},
            placeHolderText = "Password",
            keyboardType = KeyboardType.Password
        )
    }
}

@Composable
private fun ForgotPassword(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = "Forgot Password ?",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
private fun SignInButton(modifier: Modifier) {
    OutBtnDefault(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = { /*TODO*/ },
        buttonText = "Sign In"
    )
}

@Composable
private fun RegisterNow(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Not a member ?", style = MaterialTheme.typography.body2)
        TextButton(modifier = modifier.padding(start = 1.dp), onClick = { /*TODO*/ }) {
            Text(
                text = "Register now",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}