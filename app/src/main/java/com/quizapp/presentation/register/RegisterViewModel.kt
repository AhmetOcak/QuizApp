package com.quizapp.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.EmailController
import com.quizapp.core.common.Response
import com.quizapp.core.navigation.NavScreen
import com.quizapp.core.navigation.Navigator
import com.quizapp.domain.model.auth.User
import com.quizapp.domain.usecase.auth.CreateUserUseCase
import com.quizapp.presentation.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _createUserState = MutableStateFlow<CreateUserState>(CreateUserState.Nothing)
    val createUserState = _createUserState.asStateFlow()

    private val _registerInputFieldState = MutableStateFlow<RegisterInputFieldState>(RegisterInputFieldState.Nothing)
    val registerInputFieldState = _registerInputFieldState.asStateFlow()

    var userName by mutableStateOf("")
        private set
    var userEmail by mutableStateOf("")
        private set
    var userPassword by mutableStateOf("")
        private set
    var userConfirmPassword by mutableStateOf("")
        private set

    var userNameError by mutableStateOf(false)
        private set
    var userEmailError by mutableStateOf(false)
        private set
    var userPasswordError by mutableStateOf(false)
        private set
    var userConfirmPasswordError by mutableStateOf(false)
        private set

    fun updateUserNameField(newValue: String) { userName = newValue }
    fun updateUserEmailField(newValue: String) { userEmail = newValue }
    fun updateUserPasswordField(newValue: String) { userPassword = newValue }
    fun updateUserConfirmPassword(newValue: String) { userConfirmPassword = newValue }

    fun createUser() = viewModelScope.launch(Dispatchers.IO) {
        if (checkRegisterInputField()) {
            createUserUseCase(
                user = User(
                    userName = userName,
                    password = userPassword,
                    email = userEmail
                )
            ).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _createUserState.value = CreateUserState.Loading
                    }
                    is Response.Success -> {
                        _createUserState.value = CreateUserState.Success
                        Navigator.navigate(NavScreen.SignInScreen.route) {
                            popUpTo(NavScreen.RegisterScreen.route)
                        }
                    }
                    is Response.Error -> {
                        _createUserState.value = CreateUserState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }

    private fun checkRegisterInputField(): Boolean =
        if (checkInputFields() && checkUserEmail() && checkUserPassword() && checkUserName() && confirmUserPassword()) {
            _registerInputFieldState.value = RegisterInputFieldState.Nothing
            true
        } else {
            false
        }

    private fun checkInputFields(): Boolean =
        if (userName.isBlank() || userEmail.isBlank() || userPassword.isBlank() || userConfirmPassword.isBlank()) {
            _registerInputFieldState.value = RegisterInputFieldState.Error(errorMessage = Messages.FILL)

            userNameError = true
            userEmailError = true
            userPasswordError = true
            userConfirmPasswordError = true

            false
        } else {
            _registerInputFieldState.value = RegisterInputFieldState.Nothing

            userNameError = false
            userEmailError = false
            userPasswordError = false
            userConfirmPasswordError = false

            true
        }

    private fun checkUserEmail(): Boolean =
        if (EmailController.isEmailType(userEmail)) {
            userEmailError = false

            true
        } else {
            _registerInputFieldState.value = RegisterInputFieldState.Error(errorMessage = Messages.VALID_EMAIL)
            userEmailError = true
            false
        }

    private fun checkUserPassword(): Boolean =
        if (userPassword.length < 6) {
            _registerInputFieldState.value = RegisterInputFieldState.Error(errorMessage = Messages.LENGTH_PASSWORD)
            userPasswordError = true
            false
        } else {
            userPasswordError = false
            true
        }

    private fun checkUserName(): Boolean =
        if (userName.length < 3) {
            _registerInputFieldState.value = RegisterInputFieldState.Error(errorMessage = Messages.USER_NAME_LENGTH)
            userNameError = true
            false
        } else {
            userNameError = false
            true
        }

    private fun confirmUserPassword(): Boolean =
        if (userPassword == userConfirmPassword) {
            userPasswordError = false
            userConfirmPasswordError = false

            true
        } else {
            _registerInputFieldState.value = RegisterInputFieldState.Error(errorMessage = Messages.PASSWORD_MATCH)

            userPasswordError = true
            userConfirmPasswordError = true

            false
        }

    fun resetRegisterState() { _registerInputFieldState.value = RegisterInputFieldState.Nothing }

/*    fun resetInputErrors() {
        userNameError = false
        userEmailError = false
        userPasswordError = false
        userConfirmPasswordError = false
    }*/
}