package com.quizapp.presentation.profile

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.common.getToken
import com.quizapp.domain.model.user.UserProfile
import com.quizapp.domain.usecase.quiz.GetUserQuizzesUseCase
import com.quizapp.domain.usecase.user.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getUserQuizzesUseCase: GetUserQuizzesUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _getUserProfileState = MutableStateFlow<GetUserProfileState>(GetUserProfileState.Loading)
    val getUserProfileState = _getUserProfileState.asStateFlow()

    private val _getUserQuizzesState = MutableStateFlow<GetUserQuizzesState>(GetUserQuizzesState.Loading)
    val getUserQuizzesState = _getUserQuizzesState.asStateFlow()

    private var token: String = ""

    var userData: UserProfile? = null
        private set

    init {
        token = sharedPreferences.getToken() ?: ""
        getUserProfile()
        getUserQuizzes()
    }

    private fun getUserProfile() = viewModelScope.launch(Dispatchers.IO) {
        getUserProfileUseCase(token = "Bearer $token").collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _getUserProfileState.value = GetUserProfileState.Loading
                }
                is Response.Success -> {
                    _getUserProfileState.value = GetUserProfileState.Success(data = response.data)
                    userData = response.data
                }
                is Response.Error -> {
                    _getUserProfileState.value = GetUserProfileState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    private fun getUserQuizzes() = viewModelScope.launch(Dispatchers.IO) {
        getUserQuizzesUseCase(token = "Bearer $token").collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _getUserQuizzesState.value = GetUserQuizzesState.Loading
                }
                is Response.Success -> {
                    _getUserQuizzesState.value = GetUserQuizzesState.Success(data = response.data)
                }
                is Response.Error -> {
                    _getUserQuizzesState.value = GetUserQuizzesState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}