package com.quizapp.presentation.reset_password

sealed interface ResetPasswordInputFieldState {
    object Nothing : ResetPasswordInputFieldState
    data class Error(val errorMessage: String) : ResetPasswordInputFieldState
}