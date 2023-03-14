package com.quizapp.presentation.profile

import com.quizapp.domain.model.quiz.UserQuizzes

sealed interface GetUserQuizzesState {
    object Loading : GetUserQuizzesState
    data class Success(val data: UserQuizzes) : GetUserQuizzesState
    data class Error(val errorMessage: String) : GetUserQuizzesState
}