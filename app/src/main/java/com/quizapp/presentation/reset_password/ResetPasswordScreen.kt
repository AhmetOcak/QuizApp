package com.quizapp.presentation.reset_password

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.R
import com.quizapp.core.navigation.Navigator
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.OnBackPressed
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.component.OutBtnCustom

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {

    val resetPasswordState by viewModel.resetPasswordState.collectAsState()
    val resetPasswordInputFieldState by viewModel.resetPasswordInputFieldState.collectAsState()

    val activity = LocalContext.current as Activity
    OnBackPressed(activity = activity)

    ForgotPasswordScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        resetPasswordState = resetPasswordState,
        resetPasswordInputFieldState = resetPasswordInputFieldState,
        activity = activity
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ForgotPasswordScreenContent(
    modifier: Modifier,
    viewModel: ResetPasswordViewModel,
    resetPasswordState: ResetPasswordState,
    resetPasswordInputFieldState: ResetPasswordInputFieldState,
    activity: Activity
) {
    Scaffold(modifier = modifier) {
        when (resetPasswordState) {
            is ResetPasswordState.Nothing -> {
                ChangePasswordSection(modifier = modifier, viewModel = viewModel)
            }
            is ResetPasswordState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CustomLoadingSpinner()
                }
            }
            is ResetPasswordState.Success -> {
                ChangePasswordSuccessSection(modifier = modifier, activity = activity)
            }
            is ResetPasswordState.Error -> {
                Log.e("error forgot screen", resetPasswordState.errorMessage)
                ChangePasswordSection(modifier = modifier, viewModel = viewModel)
                ShowMessage(message = resetPasswordState.errorMessage)
            }
        }
        ShowInputFieldErrors(
            forgotPasswordInputFieldState = resetPasswordInputFieldState,
            viewModel = viewModel
        )
    }
}

@Composable
private fun ChangePasswordSection(modifier: Modifier, viewModel: ResetPasswordViewModel) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DefaultImage(modifier = modifier, imageId = R.drawable.forgot_password_email)
        DefaultText(
            modifier = modifier,
            text = "Your new password must be different from previously used password"
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onValueChanged = { viewModel.updateNewPassword(it) },
            value = viewModel.newPassword,
            placeHolderText = "News Password",
            keyboardType = KeyboardType.Password,
            isError = viewModel.newPasswordError
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onValueChanged = { viewModel.updateConfirmNewPassword(it) },
            value = viewModel.confirmNewPassword,
            placeHolderText = "Confirm Password",
            keyboardType = KeyboardType.Password,
            isError = viewModel.confirmNewPasswordError
        )
        OutBtnCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            onClick = { viewModel.resetPassword() },
            buttonText = "Save"
        )
    }
}

@Composable
private fun ChangePasswordSuccessSection(modifier: Modifier, activity: Activity) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DefaultImage(modifier = modifier, imageId = R.drawable.forgot_password_success)
        DefaultText(
            modifier = modifier.padding(horizontal = 8.dp),
            text = "Your password has been reset successfully"
        )
        OutBtnCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = {
                Navigator.resetDestination()
                activity.finish()
            },
            buttonText = "Ok"
        )
    }
}

// Created for this screen (image comp)
@Composable
private fun DefaultImage(modifier: Modifier, imageId: Int) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        painter = painterResource(id = imageId),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}

// Created for this screen (text comp)
@Composable
private fun DefaultText(modifier: Modifier, text: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        text = text,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun ShowInputFieldErrors(
    forgotPasswordInputFieldState: ResetPasswordInputFieldState,
    viewModel: ResetPasswordViewModel
) {
    when (forgotPasswordInputFieldState) {
        is ResetPasswordInputFieldState.Error -> {
            Toast.makeText(
                LocalContext.current,
                forgotPasswordInputFieldState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetForgotPasswordInputState()
        }
        is ResetPasswordInputFieldState.Nothing -> {}
    }
}

@Composable
private fun ShowMessage(message: String) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_LONG
    ).show()
}