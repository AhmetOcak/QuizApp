package com.quizapp.presentation.confirm_account

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.domain.model.confirm_account.ConfirmAccount
import com.quizapp.domain.usecase.confirm_account.ConfirmAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmAccountViewModel @Inject constructor(
    private val confirmAccountUseCase: ConfirmAccountUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _confirmAccountState = MutableStateFlow<ConfirmAccountState>(ConfirmAccountState.Nothing)
    val confirmAccountState = _confirmAccountState.asStateFlow()

    private val email: String? = savedStateHandle["email"]
    private val token: String? = savedStateHandle["token"]

    fun confirmAccount() = viewModelScope.launch(Dispatchers.IO) {
        Log.e("email => ", email ?: "null")
        Log.e("token => ", token ?: "null")
        if (email != null && token != null) {
            confirmAccountUseCase(confirmAccount = ConfirmAccount(email = email, token = token)).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        Log.e("res", "loading")
                        _confirmAccountState.value = ConfirmAccountState.Loading
                    }
                    is Response.Success -> {
                        Log.e("res", "success")
                        _confirmAccountState.value = ConfirmAccountState.Success
                    }
                    is Response.Error -> {
                        Log.e("error", response.errorMessage)
                        _confirmAccountState.value =
                            ConfirmAccountState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        } else {
            _confirmAccountState.value = ConfirmAccountState.Error(errorMessage = "Something went wrong. Try again later.")
            Log.e("email or token null", ":/")
        }
    }
}