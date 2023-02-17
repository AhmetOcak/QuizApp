package com.quizapp.presentation.reset_password

sealed interface ResetPasswordState {
    object Nothing : ResetPasswordState
    object Loading : ResetPasswordState
    object Success : ResetPasswordState
    data class Error(val errorMessage: String) : ResetPasswordState
}