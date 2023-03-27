package com.quizapp.presentation.update_quiz

import com.quizapp.domain.model.option.Options

sealed interface GetOptionsState {
    object Loading : GetOptionsState
    data class Success(val data: Options) : GetOptionsState
    data class Error(val errorMessage: String) : GetOptionsState
}