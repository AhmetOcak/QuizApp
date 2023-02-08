package com.quizapp.presentation.signin

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.component.OutBtnCustom

@Composable
fun SignInScreen(modifier: Modifier = Modifier, viewModel: SignInViewModel = hiltViewModel()) {

    val signInState by viewModel.signInState.collectAsState()
    val signInInputFieldState by viewModel.signInInputFieldState.collectAsState()

    SignInScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        signInState = signInState,
        signInInputFieldState = signInInputFieldState
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun SignInScreenContent(
    modifier: Modifier,
    viewModel: SignInViewModel,
    signInState: SignInState,
    signInInputFieldState: SignInInputFieldState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp),
        verticalArrangement = Arrangement.Center
    ) {
        when (signInState) {
            is SignInState.Nothing -> {
                TitleSection(modifier = modifier)
                SignInSection(modifier = modifier, viewModel = viewModel)
                SignInButton(modifier = modifier, viewModel = viewModel)
                RegisterNow(modifier = modifier)
            }
            is SignInState.Loading -> {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CustomLoadingSpinner()
                }
            }
            is SignInState.Success -> {
                Log.e("sign in", "Success => " + signInState.data.token.accessToken)
            }
            is SignInState.Error -> {
                Log.e("sign in", "Error => " + signInState.errorMessage)
            }
        }
        ShowInputFieldErrors(signInInputFieldState = signInInputFieldState, viewModel = viewModel)
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
            style = MaterialTheme.typography.h1.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
        Text(
            modifier = modifier.padding(top = 16.dp, start = 54.dp, end = 54.dp),
            text = "Welcome back you've been missed",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun SignInSection(modifier: Modifier, viewModel: SignInViewModel) {
    SignInInput(modifier = modifier, viewModel = viewModel)
    ForgotPassword(modifier = modifier)
}

@Composable
private fun SignInInput(modifier: Modifier, viewModel: SignInViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OtfCustom(
            modifier = modifier.fillMaxWidth(),
            onValueChanged = { viewModel.updateEmailField(newValue = it) },
            placeHolderText = "Enter email",
            keyboardType = KeyboardType.Email,
            value = viewModel.email,
            isError = viewModel.emailError
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = { viewModel.updatePasswordField(newValue = it) },
            placeHolderText = "Password",
            keyboardType = KeyboardType.Password,
            value = viewModel.password,
            isError = viewModel.passwordError
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
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
private fun SignInButton(modifier: Modifier, viewModel: SignInViewModel) {
    OutBtnCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = {
            viewModel.signIn()
            viewModel.resetSignState()
        },
        buttonText = "Sign In"
    )
}

@Composable
private fun RegisterNow(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Not a member ?", style = MaterialTheme.typography.body2)
        TextButton(modifier = modifier.padding(start = 1.dp), onClick = { /*TODO*/ }) {
            Text(
                text = "Register now",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
private fun ShowInputFieldErrors(
    signInInputFieldState: SignInInputFieldState,
    viewModel: SignInViewModel
) {
    when (signInInputFieldState) {
        is SignInInputFieldState.Error -> {
            Toast.makeText(
                LocalContext.current,
                signInInputFieldState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetSignState()
        }
        is SignInInputFieldState.Nothing -> {}
    }
}