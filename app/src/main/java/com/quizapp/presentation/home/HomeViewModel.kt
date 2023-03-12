package com.quizapp.presentation.home

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.common.getToken
import com.quizapp.domain.usecase.user.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState = _userState.asStateFlow()

    private var token: String? = null

    init {
        getToken()
        getUserProfile()
    }

    private fun getToken() {
        token = sharedPreferences.getToken() ?: ""
    }

    private fun getUserProfile() = viewModelScope.launch(Dispatchers.IO) {
        getUserProfileUseCase(token = "Bearer $token").collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _userState.value = UserState.Loading
                }
                is Response.Success -> {
                    _userState.value = UserState.Success(data = response.data)
                }
                is Response.Error -> {
                    _userState.value = UserState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}