package com.quizapp.presentation.edit_profile

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.quizapp.core.common.removeToken
import com.quizapp.core.navigation.EditProfileScreenArgs
import com.quizapp.core.navigation.NavScreen
import com.quizapp.core.navigation.Navigator
import com.quizapp.presentation.utils.EditProfileScreenPreferencesNames
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class Sections {
    PROFILE,
    CONTACT_US
}

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var currentSection by mutableStateOf(Sections.PROFILE)
        private set
    var topBarTitle by mutableStateOf("Profile")
        private set

    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var userName by mutableStateOf("")
        private set
    var profilePicture by mutableStateOf("")
        private set

    init {
        firstName = savedStateHandle[EditProfileScreenArgs.FIRST_NAME] ?: "-"
        lastName = savedStateHandle[EditProfileScreenArgs.LAST_NAME] ?: "-"
        userName = savedStateHandle[EditProfileScreenArgs.USER_NAME] ?: "-"
        profilePicture = savedStateHandle[EditProfileScreenArgs.USER_PROFILE_IMG] ?: ""
    }

    fun updateCurrentSection(newSection: Sections) { currentSection = newSection }

    fun setTopBarTitle() {
        topBarTitle = when(currentSection) {
            Sections.PROFILE -> { "Profile" }
            Sections.CONTACT_US -> { "Contact Us" }
        }
    }

    fun setPreferenceOnClick(preferenceName: String) {
        when(preferenceName) {
            EditProfileScreenPreferencesNames.LANGUAGE -> { }
            EditProfileScreenPreferencesNames.DARK_MODE -> { }
            EditProfileScreenPreferencesNames.INFORMATION -> { }
            EditProfileScreenPreferencesNames.CHANGE_PASSWORD -> {
                Navigator.navigate(NavScreen.UpdatePasswordScreen.route) {}
            }
            EditProfileScreenPreferencesNames.CONTACT_US -> {
                updateCurrentSection(Sections.CONTACT_US)
                setTopBarTitle()
            }
            EditProfileScreenPreferencesNames.DELETE_ACCOUNT -> {
                Navigator.navigate(NavScreen.DeleteAccountScreen.route) {}
            }
            EditProfileScreenPreferencesNames.LOG_OUT -> {
                with(sharedPreferences.edit()) {
                    removeToken()
                }
                Navigator.navigate(NavScreen.SignInScreen.route) {
                    popUpTo(0)
                }
            }
            else -> { }
        }
    }
}