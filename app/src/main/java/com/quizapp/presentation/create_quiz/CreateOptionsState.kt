package com.quizapp.presentation.create_quiz

sealed interface CreateOptionsState {
    object Loading : CreateOptionsState
    object Success : CreateOptionsState
    data class Error(val errorMessage: String) : CreateOptionsState
}