package com.quizapp.presentation.signin

sealed interface SignInInputFieldState {
    data class Error(val errorMessage: String) : SignInInputFieldState
    object Nothing : SignInInputFieldState
}