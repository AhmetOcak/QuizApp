package com.quizapp.presentation.update_quiz

sealed interface UpdateQuizState {
    object Nothing : UpdateQuizState
    object Loading : UpdateQuizState
    object Success : UpdateQuizState
    data class Error(val errorMessage: String) : UpdateQuizState
}