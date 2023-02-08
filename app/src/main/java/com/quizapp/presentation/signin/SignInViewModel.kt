package com.quizapp.presentation.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.domain.model.auth.Login
import com.quizapp.domain.usecase.auth.SignInUseCase
import com.quizapp.presentation.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Nothing)
    val signInState = _signInState.asStateFlow()

    private val _signInInputFieldState = MutableStateFlow<SignInInputFieldState>(SignInInputFieldState.Nothing)
    val signInInputFieldState = _signInInputFieldState.asStateFlow()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var emailError by mutableStateOf(false)
        private set
    var passwordError by mutableStateOf(false)
        private set

    fun signIn() = viewModelScope.launch(Dispatchers.IO) {
        if (checkInputFields()) {
            signInUseCase(login = Login(email = email, password = password)).collect() { response ->
                when(response) {
                    is Response.Loading -> {
                        _signInState.value = SignInState.Loading
                    }
                    is Response.Success -> {
                        _signInState.value = SignInState.Success(data = response.data)
                    }
                    is Response.Error -> {
                        _signInState.value = SignInState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }

    fun updateEmailField(newValue: String) { email = newValue }
    fun updatePasswordField(newValue: String) { password = newValue }

    fun resetSignState() { _signInInputFieldState.value = SignInInputFieldState.Nothing }

    private fun checkInputFields(): Boolean =
        if (email.isBlank() && password.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            emailError = true
            passwordError = true
            false
        } else if (email.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            emailError = true
            passwordError = false
            false
        } else if (password.isBlank()) {
            _signInInputFieldState.value = SignInInputFieldState.Error(errorMessage = Messages.FILL)
            passwordError = true
            emailError = false
            false
        } else {
            emailError = false
            passwordError = false
            true
        }
}