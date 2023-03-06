package com.quizapp.presentation.update_password

sealed interface UpdatePasswordState {
    object Nothing : UpdatePasswordState
    object Loading : UpdatePasswordState
    object Success : UpdatePasswordState
    data class Error(val errorMessage: String) : UpdatePasswordState
}