package com.quizapp.presentation.confirm_account

sealed interface ConfirmAccountState {
    object Nothing : ConfirmAccountState
    object Loading : ConfirmAccountState
    object Success : ConfirmAccountState
    data class Error(val errorMessage: String) : ConfirmAccountState
}