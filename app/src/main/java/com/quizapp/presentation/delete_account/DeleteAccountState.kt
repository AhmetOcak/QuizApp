package com.quizapp.presentation.delete_account

sealed interface DeleteAccountState {
    object Nothing : DeleteAccountState
    object Loading : DeleteAccountState
    object Success : DeleteAccountState
    data class Error(val errorMessage: String) : DeleteAccountState
}