package com.quizapp.presentation.reset_password

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.domain.model.reset_password.ResetPassword
import com.quizapp.domain.usecase.reset_password.ResetPasswordUseCase
import com.quizapp.presentation.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _resetPasswordState = MutableStateFlow<ResetPasswordState>(ResetPasswordState.Nothing)
    val resetPasswordState = _resetPasswordState.asStateFlow()

    private val _resetPasswordInputFieldState = MutableStateFlow<ResetPasswordInputFieldState>(ResetPasswordInputFieldState.Nothing)
    val resetPasswordInputFieldState = _resetPasswordInputFieldState.asStateFlow()

    var newPassword by mutableStateOf("")
        private set
    var confirmNewPassword by mutableStateOf("")
        private set

    // Text field error trigger
    var newPasswordError by mutableStateOf(false)
        private set
    var confirmNewPasswordError by mutableStateOf(false)
        private set

    private val token: String? = savedStateHandle["token"]
    private val email: String? = savedStateHandle["email"]

    fun updateNewPassword(newValue: String) { newPassword = newValue }

    fun updateConfirmNewPassword(newValue: String) { confirmNewPassword = newValue }

    fun resetPassword() = viewModelScope.launch(Dispatchers.IO) {
        Log.e("email => ", email ?: "null")
        Log.e("token => ", token ?: "null")

        if (checkInputFields() && checkNewPassword() && checkPasswordsMatches()) {
            if (token != null && email != null) {
                resetPasswordUseCase(
                    resetPassword = ResetPassword(
                        email = email,
                        token = token,
                        newPassword = newPassword
                    )
                ).collect() { response ->
                    when (response) {
                        is Response.Loading -> {
                            Log.e("reset password", "loading")
                            _resetPasswordState.value = ResetPasswordState.Loading
                        }
                        is Response.Success -> {
                            Log.e("reset password", "success")
                            _resetPasswordState.value = ResetPasswordState.Success
                        }
                        is Response.Error -> {
                            Log.e("reset password", response.errorMessage)
                            _resetPasswordState.value = ResetPasswordState.Error(errorMessage = response.errorMessage)
                        }
                    }
                }
            } else {
                _resetPasswordState.value = ResetPasswordState.Error(errorMessage = "Something went wrong. Try again later.")
                Log.e("email or token null", ":/")
            }
        }
    }

    fun resetForgotPasswordInputState() {
        _resetPasswordInputFieldState.value = ResetPasswordInputFieldState.Nothing
    }

    private fun checkNewPassword(): Boolean {
        return if (newPassword.length < 6) {
            _resetPasswordInputFieldState.value = ResetPasswordInputFieldState.Error(errorMessage = Messages.LENGTH_PASSWORD)
            newPasswordError = true
            confirmNewPasswordError = false
            false
        } else {
            newPasswordError = false
            true
        }
    }

    private fun checkPasswordsMatches(): Boolean {
        return if (newPassword != confirmNewPassword) {
            _resetPasswordInputFieldState.value = ResetPasswordInputFieldState.Error(errorMessage = Messages.PASSWORD_MATCH)
            confirmNewPasswordError = true
            false
        } else {
            confirmNewPasswordError = false
            true
        }
    }

    private fun checkInputFields(): Boolean {
        return if (newPassword.isBlank() || confirmNewPassword.isBlank()) {
            _resetPasswordInputFieldState.value = ResetPasswordInputFieldState.Error(errorMessage = Messages.FILL)
            newPasswordError = true
            confirmNewPasswordError = true
            false
        } else {
            newPasswordError = false
            confirmNewPasswordError = false
            true
        }
    }
}