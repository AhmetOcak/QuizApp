package com.quizapp.presentation.update_quiz

sealed interface UpdateAnswerState {
    object Loading : UpdateAnswerState
    object Nothing : UpdateAnswerState
    object Success : UpdateAnswerState
    data class Error(val errorMessage: String) : UpdateAnswerState
}