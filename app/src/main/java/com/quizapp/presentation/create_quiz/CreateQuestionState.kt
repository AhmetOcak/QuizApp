package com.quizapp.presentation.create_quiz

sealed interface CreateQuestionState {
    object Nothing : CreateQuestionState
    object Loading : CreateQuestionState
    object Success : CreateQuestionState
    data class Error(val errorMessage: String) : CreateQuestionState
}