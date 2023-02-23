package com.quizapp.presentation.profile

import com.quizapp.domain.model.user.UserProfile

sealed interface GetUserProfileState {
    object Loading : GetUserProfileState
    data class Success(val data: UserProfile) : GetUserProfileState
    data class Error(val errorMessage: String) : GetUserProfileState
}