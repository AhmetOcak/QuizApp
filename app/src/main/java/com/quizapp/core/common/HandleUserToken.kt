package com.quizapp.core.common

import android.content.SharedPreferences

fun SharedPreferences.Editor.storeToken(token: String) {
    putString("token", token)
    apply()
}

fun SharedPreferences.getToken(): String? {
    return getString("token", "")
}

fun SharedPreferences.Editor.removeToken() {
    remove("token")
    apply()
}