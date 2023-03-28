package com.quizapp.presentation.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.common.getToken
import com.quizapp.domain.usecase.category.GetCategoriesUseCase
import com.quizapp.domain.usecase.user.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState = _userState.asStateFlow()

    private val _categoriesState = MutableStateFlow<CategoriesState>(CategoriesState.Loading)
    val categoriesState = _categoriesState.asStateFlow()

    private var token: String? = null

    init {
        getToken()
        getUserProfile()
        getCategories()
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

    private fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        getCategoriesUseCase().collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _categoriesState.value = CategoriesState.Loading
                }
                is Response.Success -> {
                    _categoriesState.value = CategoriesState.Success(data = response.data)
                }
                is Response.Error -> {
                    _categoriesState.value = CategoriesState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }
}