package com.quizapp.core.ui.component

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.quizapp.core.navigation.Navigator

// Created for bottom navigation screens
@Composable
fun OnBackPressed(activity: Activity) {
    BackHandler {
        activity.finish()
    }
}

// Created for normal screens
// Navigate back screen
@Composable
fun OnBackPressed(targetRoute: String) {
    BackHandler() {
        Navigator.navigate(destination = targetRoute) {}
    }
}