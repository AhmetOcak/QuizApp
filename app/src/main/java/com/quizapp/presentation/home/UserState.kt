package com.quizapp.presentation.home

import com.quizapp.domain.model.user.UserProfile

sealed interface UserState {
    object Nothing : UserState
    object Loading : UserState
    data class Success(val data: UserProfile) : UserState
    data class Error(val errorMessage: String) : UserState
}