package com.quizapp.presentation.update_password

sealed interface ShowChangePasswordInputErrors {
    object Nothing : ShowChangePasswordInputErrors
    data class Error(val message: String) : ShowChangePasswordInputErrors
}