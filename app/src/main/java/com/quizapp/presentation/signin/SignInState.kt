package com.quizapp.presentation.signin

import com.quizapp.domain.model.auth.LoginResponse

sealed interface SignInState {
    data class Success(val data: LoginResponse) : SignInState
    data class Error(val errorMessage: String) : SignInState
    object Loading : SignInState
    object Nothing : SignInState
}