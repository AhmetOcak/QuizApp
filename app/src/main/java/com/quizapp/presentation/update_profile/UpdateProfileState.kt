package com.quizapp.presentation.update_profile

sealed interface UpdateProfileState {
    object Nothing : UpdateProfileState
    object Loading : UpdateProfileState
    object Success : UpdateProfileState
    data class Error(val errorMessage: String) : UpdateProfileState
}