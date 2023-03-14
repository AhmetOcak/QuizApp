package com.quizapp.presentation.confirm_account

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.navigation.ConfirmAccountScreenArgs
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

    private val email: String? = savedStateHandle[ConfirmAccountScreenArgs.EMAIL]
    private val token: String? = savedStateHandle[ConfirmAccountScreenArgs.TOKEN]

    fun confirmAccount() = viewModelScope.launch(Dispatchers.IO) {
        if (email != null && token != null) {
            confirmAccountUseCase(confirmAccount = ConfirmAccount(email = email, token = token)).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _confirmAccountState.value = ConfirmAccountState.Loading
                    }
                    is Response.Success -> {
                        _confirmAccountState.value = ConfirmAccountState.Success
                    }
                    is Response.Error -> {
                        _confirmAccountState.value = ConfirmAccountState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        } else {
            _confirmAccountState.value = ConfirmAccountState.Error(errorMessage = "Something went wrong. Try again later.")
            Log.e("confirm account", "email or token null")
        }
    }

    fun resetConfirmAccountState() { _confirmAccountState.value = ConfirmAccountState.Nothing }
}