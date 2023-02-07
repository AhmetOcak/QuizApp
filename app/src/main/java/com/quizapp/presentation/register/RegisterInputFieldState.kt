package com.quizapp.presentation.register

sealed interface RegisterInputFieldState {
    data class Error(val errorMessage: String) : RegisterInputFieldState
    object Nothing : RegisterInputFieldState
}