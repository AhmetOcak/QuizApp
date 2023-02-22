package com.quizapp.presentation.create_quiz

sealed interface CreateQuestionInputFieldState {
    object Nothing : CreateQuestionInputFieldState
    data class Error(val errorMessage: String) : CreateQuestionInputFieldState
}