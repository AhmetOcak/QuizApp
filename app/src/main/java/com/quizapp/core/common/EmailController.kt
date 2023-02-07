package com.quizapp.core.common

import android.util.Patterns.EMAIL_ADDRESS

object EmailController {

    fun isEmailType(email: String): Boolean = EMAIL_ADDRESS.matcher(email).matches()
}