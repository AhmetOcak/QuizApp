package com.quizapp.presentation.leaderboard

import com.quizapp.domain.model.user.Leaderboard

sealed interface LeaderboardState {
    object Loading : LeaderboardState
    data class Success(val data: ArrayList<Leaderboard>) : LeaderboardState
    data class Error(val errorMessage: String) : LeaderboardState
}