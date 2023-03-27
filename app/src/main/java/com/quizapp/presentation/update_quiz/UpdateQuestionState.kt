package com.quizapp.presentation.update_quiz

sealed interface UpdateQuestionState {
    object Nothing : UpdateQuestionState
    object Loading : UpdateQuestionState
    object Success : UpdateQuestionState
    data class Error(val errorMessage: String) : UpdateQuestionState
}