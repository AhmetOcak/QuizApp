package com.quizapp.presentation.delete_account

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.common.getToken
import com.quizapp.core.common.getUserIdFromToken
import com.quizapp.domain.usecase.user.DeleteAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _deleteAccountState = MutableStateFlow<DeleteAccountState>(DeleteAccountState.Nothing)
    val deleteAccountState = _deleteAccountState.asStateFlow()

    private var token: String? = null
    private var userId: String? = null

    init {
        token = sharedPreferences.getToken()
        userId = token?.let { getUserIdFromToken(it) }
    }

    fun deleteAccount() = viewModelScope.launch(Dispatchers.IO) {
        deleteAccountUseCase.invoke("$userId").collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _deleteAccountState.value = DeleteAccountState.Loading
                }
                is Response.Success -> {
                    _deleteAccountState.value = DeleteAccountState.Success
                }
                is Response.Error -> {
                    _deleteAccountState.value = DeleteAccountState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun resetDeleteAccountState() { _deleteAccountState.value = DeleteAccountState.Nothing }
}