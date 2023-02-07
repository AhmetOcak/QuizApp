package com.quizapp.presentation.register

sealed interface CreateUserState {
    object Success : CreateUserState
    data class Error(val errorMessage: String) : CreateUserState
    object Loading : CreateUserState
    object Nothing : CreateUserState
}