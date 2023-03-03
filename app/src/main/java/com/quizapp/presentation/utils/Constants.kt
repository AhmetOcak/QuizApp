package com.quizapp.presentation.utils

import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.unit.dp

object Dimens {
    val AppBarDefaultHeight = 56.dp
    val ConstantHeight = TextFieldDefaults.MinHeight + 8.dp // Default button, text field height
}

object Messages {
    const val FILL = "Please fill all fields"
    const val VALID_EMAIL = "Please enter a valid email address"
    const val LENGTH_PASSWORD = "Password length can't be less than 6"
    const val USER_NAME_LENGTH = "Username length can't be less than 3"
    const val PASSWORD_MATCH = "Passwords doesn't match"
    const val RESET_PASS_MAIL = "We've sent you a password reset email. Please check your mail."
    const val ACCOUNT_VERIFIED = "Your account has been confirmed. You can now login to your Account."
    const val USER_CREATE_SUCCESS = "The user has been successfully created. We have sent you an activation email. Please check your mail."
    const val CREATE_QUIZ_SUCCESS = "Successfully created."
    const val CREATE_QUEST_SUCCESS = "Successfully created."
    const val FILL_ANSWERS = "Please fill all answer fields"
    const val SELECT_TRUE_ANSWER = "Please select a true answer"
    const val SELECT_CATEGORY = "Please select a category"
}