package com.quizapp.presentation.update_password

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.common.getToken
import com.quizapp.domain.model.user.UpdatePasswordBody
import com.quizapp.domain.usecase.user.UpdatePasswordUseCase
import com.quizapp.presentation.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _updatePasswordState = MutableStateFlow<UpdatePasswordState>(UpdatePasswordState.Nothing)
    val updatePasswordState = _updatePasswordState.asStateFlow()

    private val _showChangePasswordErrorInpErrors = MutableStateFlow<ShowChangePasswordInputErrors>(ShowChangePasswordInputErrors.Nothing)
    val showChangePasswordErrorInpErrors = _showChangePasswordErrorInpErrors.asStateFlow()

    private var token: String? = null

    var oldPassword by mutableStateOf("")
        private set
    var newPassword by mutableStateOf("")
        private set
    var confirmNewPassword by mutableStateOf("")
        private set

    var oldPasswordError by mutableStateOf(false)
        private set
    var newPasswordError by mutableStateOf(false)
        private set
    var confirmNewPasswordError by mutableStateOf(false)
        private set

    init {
        token = sharedPreferences.getToken()
    }

    fun updateOldPasswordField(newValue: String) { oldPassword = newValue }
    fun updateNewPasswordField(newValue: String) { newPassword = newValue }
    fun updateConfirmNewPasswordField(newValue: String) { confirmNewPassword = newValue }

    fun updatePassword() = viewModelScope.launch(Dispatchers.IO) {
        if (checkChangePasswordInputs()) {
            token?.let {
                updatePasswordUseCase(
                    token = "Bearer $it",
                    updatePasswordBody = UpdatePasswordBody(
                        oldPassword = oldPassword,
                        newPassword = newPassword
                    )
                ).collect() { response ->
                    when(response) {
                        is Response.Loading -> {
                            _updatePasswordState.value = UpdatePasswordState.Loading
                        }
                        is Response.Success -> {
                            _updatePasswordState.value = UpdatePasswordState.Success
                        }
                        is Response.Error -> {
                            _updatePasswordState.value = UpdatePasswordState.Error(response.errorMessage)
                            Log.e("update password error", response.errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun checkChangePasswordInputs(): Boolean =
        !checkIsPasswordsBlank() && checkIsPasswordConfirmed() && checkPasswordLength()

    private fun checkIsPasswordsBlank(): Boolean {
        return if (oldPassword.isBlank() || newPassword.isBlank() || confirmNewPassword.isBlank()) {
            oldPasswordError = oldPassword.isBlank()
            newPasswordError = newPassword.isBlank()
            confirmNewPasswordError = confirmNewPassword.isBlank()
            _showChangePasswordErrorInpErrors.value = ShowChangePasswordInputErrors.Error(Messages.FILL)
            true
        }  else {
            _showChangePasswordErrorInpErrors.value = ShowChangePasswordInputErrors.Nothing
            oldPasswordError = false
            newPasswordError = false
            confirmNewPasswordError = false
            false
        }
    }

    private fun checkIsPasswordConfirmed(): Boolean {
        return if (newPassword == confirmNewPassword) {
            _showChangePasswordErrorInpErrors.value = ShowChangePasswordInputErrors.Nothing
            confirmNewPasswordError = false
            true
        } else {
            _showChangePasswordErrorInpErrors.value = ShowChangePasswordInputErrors.Error(Messages.PASSWORD_MATCH)
            confirmNewPasswordError = true
            false
        }
    }

    private fun checkPasswordLength(): Boolean {
        return if (newPassword.length > 6) {
            _showChangePasswordErrorInpErrors.value = ShowChangePasswordInputErrors.Nothing
            confirmNewPasswordError = false
            true
        } else {
            _showChangePasswordErrorInpErrors.value = ShowChangePasswordInputErrors.Error(Messages.LENGTH_PASSWORD)
            confirmNewPasswordError = true
            false
        }
    }

    fun resetUpdatePasswordState() {
        _updatePasswordState.value = UpdatePasswordState.Nothing
        oldPassword = ""
        newPassword = ""
        confirmNewPassword = ""
    }

    fun resetUpdatePasswordInputState() { _showChangePasswordErrorInpErrors.value = ShowChangePasswordInputErrors.Nothing }
}