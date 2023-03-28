package com.quizapp.presentation.leaderboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.domain.usecase.user.GetLeaderboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val getLeaderboardUseCase: GetLeaderboardUseCase
) : ViewModel() {

    private val _leaderboardState = MutableStateFlow<LeaderboardState>(LeaderboardState.Loading)
    val leaderboardState = _leaderboardState.asStateFlow()

    init {
        getLeaderboard()
    }

    private fun getLeaderboard() = viewModelScope.launch(Dispatchers.IO) {
        getLeaderboardUseCase().collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _leaderboardState.value = LeaderboardState.Loading
                }
                is Response.Success -> {
                    _leaderboardState.value = LeaderboardState.Success(data = response.data)
                }
                is Response.Error -> {
                    _leaderboardState.value = LeaderboardState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}