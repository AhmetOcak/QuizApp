package com.quizapp.presentation.update_quiz

sealed interface UpdateOptionState {
    object Nothing : UpdateOptionState
    object Loading : UpdateOptionState
    object Success : UpdateOptionState
    data class Error(val errorMessage: String) : UpdateOptionState
}