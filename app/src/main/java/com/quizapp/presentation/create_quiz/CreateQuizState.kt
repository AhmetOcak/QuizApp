package com.quizapp.presentation.create_quiz

sealed interface CreateQuizState {
    object Nothing : CreateQuizState
    object Loading : CreateQuizState
    object Success : CreateQuizState
    data class Error(val errorMessage: String) : CreateQuizState
}