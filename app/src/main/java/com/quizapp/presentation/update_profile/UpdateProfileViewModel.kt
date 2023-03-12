package com.quizapp.presentation.update_profile

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.common.getToken
import com.quizapp.domain.model.user.UpdateProfileBody
import com.quizapp.domain.usecase.user.GetUserProfileUseCase
import com.quizapp.domain.usecase.user.UpdateProfileUseCase
import com.quizapp.domain.usecase.user.UploadProfilePictureUseCase
import com.quizapp.presentation.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val uploadProfilePictureUseCase: UploadProfilePictureUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _updateProfileState = MutableStateFlow<UpdateProfileState>(UpdateProfileState.Nothing)
    val updateProfileState = _updateProfileState.asStateFlow()

    private var token: String? = null

    var profilePictureUrl by mutableStateOf("")
        private set

    // for the show to user
    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var biography by mutableStateOf("")
        private set

    // for the send edit profile screen
    var userName by mutableStateOf("")
        private set

    // for the change
    var firstNameBottomSheet by mutableStateOf("")
        private set
    var lastNameBottomSheet by mutableStateOf("")
        private set
    var biographyBottomSheet by mutableStateOf("")
        private set
    var isBiographySelected by mutableStateOf(true)
        private set

    lateinit var body: MultipartBody.Part

    init {
        token = sharedPreferences.getToken()
    }

    fun setIsBiographySelected(newValue: Boolean) { isBiographySelected = newValue }

    fun updateFirstNameField(newValue: String) { firstNameBottomSheet = newValue }

    fun updateLastNameField(newValue: String) { lastNameBottomSheet = newValue }

    fun updateBiographyField(newValue: String) { biographyBottomSheet = newValue }

    //Todo: you must test every API
    fun handleMultipartBody(filePath: String) {
        val file = File(filePath)
        val reqFile = file.asRequestBody("image/${file.extension}".toMediaTypeOrNull())
        body = MultipartBody.Part.createFormData("image", file.name, reqFile)
    }

    fun getUserProfile() = viewModelScope.launch(Dispatchers.IO) {
        getUserProfileUseCase(token = "Bearer $token").collect() { response ->
            when (response) {
                is Response.Loading -> {}
                is Response.Success -> {
                    firstName = response.data.firstName ?: "First Name"
                    lastName = response.data.lastName ?: "Last Name"
                    biography = response.data.biography ?: ""
                    userName = response.data.userName

                    firstNameBottomSheet = firstName
                    lastNameBottomSheet = lastName
                    biographyBottomSheet = biography

                    profilePictureUrl = response.data.profilePictureUrl
                }
                is Response.Error -> {
                    _updateProfileState.value = UpdateProfileState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun updateProfile() = viewModelScope.launch(Dispatchers.IO) {
        if (firstNameBottomSheet.isBlank() || lastNameBottomSheet.isBlank()) {
            _updateProfileState.value = UpdateProfileState.Error(errorMessage = Messages.UPDATE_PROF_FILL)
            firstNameBottomSheet = firstName
            lastNameBottomSheet = lastName
        } else {
            updateProfileUseCase(
                token = "Bearer $token",
                updateProfileBody = UpdateProfileBody(
                    firstName = firstNameBottomSheet,
                    lastName = lastNameBottomSheet,
                    biography = biographyBottomSheet.ifBlank { biography },
                )
            ).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _updateProfileState.value = UpdateProfileState.Loading
                    }
                    is Response.Success -> {
                        _updateProfileState.value = UpdateProfileState.Success
                        getUserProfile()
                    }
                    is Response.Error -> {
                        _updateProfileState.value = UpdateProfileState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }

    fun uploadProfilePicture() = viewModelScope.launch(Dispatchers.IO) {
        if (this@UpdateProfileViewModel::body.isInitialized) {
            uploadProfilePictureUseCase(
                token = "Bearer $token",
                filePart = body
            ).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _updateProfileState.value = UpdateProfileState.Loading
                    }
                    is Response.Success -> {
                        _updateProfileState.value = UpdateProfileState.Success
                        getUserProfile()
                    }
                    is Response.Error -> {
                        _updateProfileState.value = UpdateProfileState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }

    fun resetUpdateProfileState() { _updateProfileState.value = UpdateProfileState.Nothing }
}