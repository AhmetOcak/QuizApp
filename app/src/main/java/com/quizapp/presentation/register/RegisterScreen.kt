package com.quizapp.presentation.register

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
fun RegisterScreen(modifier: Modifier = Modifier, viewModel: RegisterViewModel = hiltViewModel()) {

    val createUserState by viewModel.createUserState.collectAsState()
    val registerInputFieldState by viewModel.registerInputFieldState.collectAsState()

    RegisterScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        registerInputFieldState = registerInputFieldState,
        createUserState = createUserState
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun RegisterScreenContent(
    modifier: Modifier,
    viewModel: RegisterViewModel,
    registerInputFieldState: RegisterInputFieldState,
    createUserState: CreateUserState
) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            when (createUserState) {
                is CreateUserState.Nothing -> {
                    TitleSection(modifier = modifier)
                    InputSection(modifier = modifier, viewModel = viewModel)
                    RegisterButton(modifier = modifier, viewModel = viewModel)
                }
                is CreateUserState.Loading -> {
                    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CustomLoadingSpinner()
                    }
                }
                is CreateUserState.Success -> {
                    Log.e("register", "Success")
                }
                is CreateUserState.Error -> {
                    Log.e("register", "error => " + createUserState.errorMessage)
                }
            }
            ShowInputFieldErrors(
                registerInputFieldState = registerInputFieldState,
                viewModel = viewModel
            )
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
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun InputSection(
    modifier: Modifier,
    viewModel: RegisterViewModel
) {
    OtfCustom(
        modifier = modifier.fillMaxWidth(),
        value = viewModel.userName,
        onValueChanged = { viewModel.updateUserNameField(newValue = it) },
        placeHolderText = "Username",
        isError = viewModel.userNameError
    )
    OtfCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = viewModel.userEmail,
        keyboardType = KeyboardType.Email,
        onValueChanged = { viewModel.updateUserEmailField(newValue = it) },
        placeHolderText = "Email",
        isError = viewModel.userEmailError
    )
    OtfCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = viewModel.userPassword,
        onValueChanged = { viewModel.updateUserPasswordField(newValue = it) },
        placeHolderText = "Password",
        keyboardType = KeyboardType.Password,
        isError = viewModel.userPasswordError
    )
    OtfCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = viewModel.userConfirmPassword,
        onValueChanged = { viewModel.updateUserConfirmPassword(newValue = it) },
        placeHolderText = "Confirm Password",
        keyboardType = KeyboardType.Password,
        isError = viewModel.userConfirmPasswordError
    )
}

@Composable
private fun RegisterButton(modifier: Modifier, viewModel: RegisterViewModel) {
    OutBtnCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = { viewModel.createUser() },
        buttonText = "Register"
    )
}

@Composable
private fun ShowInputFieldErrors(
    registerInputFieldState: RegisterInputFieldState,
    viewModel: RegisterViewModel
) {
    when (registerInputFieldState) {
        is RegisterInputFieldState.Error -> {
            Toast.makeText(
                LocalContext.current,
                registerInputFieldState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetRegisterState()
            //viewModel.resetInputErrors()
        }
        is RegisterInputFieldState.Nothing -> {}
    }
}