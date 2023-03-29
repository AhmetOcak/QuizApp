package com.quizapp.presentation.signin

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.common.storeToken
import com.quizapp.core.navigation.NavScreen
import com.quizapp.core.navigation.Navigator
import com.quizapp.domain.model.auth.Login
import com.quizapp.domain.model.reset_password.SendPasswordResetMail
import com.quizapp.domain.usecase.auth.SignInUseCase
import com.quizapp.domain.usecase.reset_password.SendPasswordResetMailUseCase
import com.quizapp.presentation.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val sendPasswordResetMailUseCase: SendPasswordResetMailUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Nothing)
    val signInState = _signInState.asStateFlow()

    private val _signInInputFieldState = MutableStateFlow<SignInInputFieldState>(SignInInputFieldState.Nothing)
    val signInInputFieldState = _signInInputFieldState.asStateFlow()

    private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Nothing)
    val forgotPasswordState = _forgotPasswordState.asStateFlow()

    // if showForgotPasswordScreen is true then it will the show enter email ui
    // if showForgotPasswordScreen is false then it will the show sign in ui
    var showForgotPasswordScreen by mutableStateOf(false)
        private set
    var forgotPasswordEmail by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var emailError by mutableStateOf(false)
        private set
    var passwordError by mutableStateOf(false)
        private set

    fun forgotPassword() = viewModelScope.launch(Dispatchers.IO) {
        sendPasswordResetMailUseCase(sendPasswordResetMail = SendPasswordResetMail(email = forgotPasswordEmail)).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _forgotPasswordState.value = ForgotPasswordState.Loading
                }
                is Response.Success -> {
                    _forgotPasswordState.value = ForgotPasswordState.Success
                }
                is Response.Error -> {
                    _forgotPasswordState.value = ForgotPasswordState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun signIn() = viewModelScope.launch(Dispatchers.IO) {
        if (checkInputFields()) {
            signInUseCase(login = Login(email = email, password = password)).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _signInState.value = SignInState.Loading
                    }
                    is Response.Success -> {
                        _signInState.value = SignInState.Success(data = response.data)

                        with(sharedPreferences.edit()) {
                            storeToken(token = response.data.token.accessToken)
                        }

                        Navigator.navigate(NavScreen.HomeScreen.route) {}
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

    fun updateForgotPasswordField(newValue: String) { forgotPasswordEmail = newValue }

    fun resetSignInputState() { _signInInputFieldState.value = SignInInputFieldState.Nothing }

    fun resetSignInState() { _signInState.value = SignInState.Nothing }

    fun resetForgotPasswordState() { _forgotPasswordState.value = ForgotPasswordState.Nothing }

    fun resetShowForgotPasScr() { showForgotPasswordScreen = false }

    fun openForgotPasswordScreen() { showForgotPasswordScreen = true }

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

    fun navigateRegisterScreen() {
        Navigator.navigate(NavScreen.RegisterScreen.route) {}
    }

    fun resetShowForPas() { showForgotPasswordScreen = false }

}