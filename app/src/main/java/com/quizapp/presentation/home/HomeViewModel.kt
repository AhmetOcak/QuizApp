package com.quizapp.presentation.home

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.quizapp.core.common.getToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    var token by mutableStateOf("")
        private set

    init {
        getToken()
    }

    private fun getToken() {
        token = sharedPreferences.getToken() ?: ""
        Log.e("user token", token)
    }
}