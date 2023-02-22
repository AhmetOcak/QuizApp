package com.quizapp.presentation.create_quiz

sealed interface CreateQuizInputFieldState {
    object Nothing : CreateQuizInputFieldState
    data class Error(val errorMessage: String) : CreateQuizInputFieldState
}