package com.quizapp.core.ui.component

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

// Created for bottom navigation screens
@Composable
fun OnBackPressed(activity: Activity) {
    BackHandler {
        activity.finish()
    }
}