package com.quizapp.presentation.update_password

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.CustomTopBarTitle
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.component.OutBtnCustom

@Composable
fun UpdatePasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdatePasswordViewModel = hiltViewModel()
) {

    val updatePasswordState by viewModel.updatePasswordState.collectAsState()
    val showChangePasswordInputErrors by viewModel.showChangePasswordErrorInpErrors.collectAsState()

    UpdatePasswordScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        updatePasswordState = updatePasswordState,
        showChangePasswordInputErrors = showChangePasswordInputErrors
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UpdatePasswordScreenContent(
    modifier: Modifier,
    viewModel: UpdatePasswordViewModel,
    updatePasswordState: UpdatePasswordState,
    showChangePasswordInputErrors: ShowChangePasswordInputErrors
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CustomTopBarTitle(
                modifier = modifier.fillMaxWidth(),
                title = "Update Password"
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ChangePasswordSection(
                modifier = modifier,
                viewModel = viewModel,
                updatePasswordState = updatePasswordState,
                showChangePasswordInputErrors = showChangePasswordInputErrors
            )
        }
    }
}

@Composable
private fun ChangePasswordSection(
    modifier: Modifier,
    viewModel: UpdatePasswordViewModel,
    updatePasswordState: UpdatePasswordState,
    showChangePasswordInputErrors: ShowChangePasswordInputErrors
) {
    when (updatePasswordState) {
        is UpdatePasswordState.Nothing -> {
            OtfCustom(
                modifier = modifier.fillMaxWidth(),
                value = viewModel.oldPassword,
                onValueChanged = { viewModel.updateOldPasswordField(it) },
                placeHolderText = "Old Password",
                keyboardType = KeyboardType.Password,
                isError = viewModel.oldPasswordError
            )
            OtfCustom(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = viewModel.newPassword,
                onValueChanged = { viewModel.updateNewPasswordField(it) },
                placeHolderText = "New Password",
                keyboardType = KeyboardType.Password,
                isError = viewModel.newPasswordError
            )
            OtfCustom(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = viewModel.confirmNewPassword,
                onValueChanged = { viewModel.updateConfirmNewPasswordField(it) },
                placeHolderText = "Confirm New Password",
                keyboardType = KeyboardType.Password,
                isError = viewModel.confirmNewPasswordError
            )
            OutBtnCustom(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                onClick = { viewModel.updatePassword() },
                buttonText = "Submit"
            )
        }
        is UpdatePasswordState.Loading -> {
            CustomLoadingSpinner()
        }
        is UpdatePasswordState.Success -> {
            Toast.makeText(
                LocalContext.current,
                "Your password updated successfully !!",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetUpdatePasswordState()
        }
        is UpdatePasswordState.Error -> {
            Toast.makeText(
                LocalContext.current,
                updatePasswordState.errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    when (showChangePasswordInputErrors) {
        is ShowChangePasswordInputErrors.Nothing -> {}
        is ShowChangePasswordInputErrors.Error -> {
            Toast.makeText(
                LocalContext.current,
                showChangePasswordInputErrors.message,
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetUpdatePasswordInputState()
        }
    }
}
