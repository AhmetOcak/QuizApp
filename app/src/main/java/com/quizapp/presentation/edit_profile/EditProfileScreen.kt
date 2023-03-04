package com.quizapp.presentation.edit_profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.quizapp.core.ui.component.CustomTopBarTitle

@Composable
fun EditProfileScreen(modifier: Modifier = Modifier) {

    EditProfileScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun EditProfileScreenContent(modifier: Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { CustomTopBarTitle(modifier = modifier, title = "Settings") }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}