package com.quizapp.presentation.forgot_password

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quizapp.R
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.component.OutBtnCustom

@Composable
fun ForgotPasswordScreen(modifier: Modifier = Modifier) {

    ForgotPasswordScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ForgotPasswordScreenContent(modifier: Modifier) {
    Scaffold(modifier = modifier) {
        EnterEmailSection(modifier = modifier)
        //ChangePasswordSection(modifier = modifier)
        //ChangePasswordSuccessSection(modifier = modifier)
    }
}

@Composable
private fun EnterEmailSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DefaultImage(modifier = modifier, imageId = R.drawable.forgot_password_main)
        DefaultText(
            modifier = modifier,
            text = "Please enter your email address to receive a verification code"
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            onValueChanged = {},
            placeHolderText = "Email"
        )
        OutBtnCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            onClick = { /*TODO*/ },
            buttonText = "Send"
        )
    }
}

@Composable
private fun ChangePasswordSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DefaultImage(modifier = modifier, imageId = R.drawable.forgot_password_email)
        DefaultText(
            modifier = modifier,
            text = "Please enter your new password and the verification code we sent you. Your new password must be different from previously used password"
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onValueChanged = {},
            placeHolderText = "Verification code",
            keyboardType = KeyboardType.Number
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onValueChanged = {},
            placeHolderText = "News Password",
            keyboardType = KeyboardType.Password
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onValueChanged = {},
            placeHolderText = "Confirm Password",
            keyboardType = KeyboardType.Password
        )
        OutBtnCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            onClick = { /*TODO*/ },
            buttonText = "Save"
        )
    }
}

@Composable
private fun ChangePasswordSuccessSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DefaultImage(modifier = modifier, imageId = R.drawable.forgot_password_success)
        DefaultText(modifier = modifier.padding(horizontal = 8.dp), text = "Your password has been reset successfully")
        OutBtnCustom(
            modifier = modifier.fillMaxWidth().padding(top = 16.dp),
            onClick = { /*TODO*/ },
            buttonText = "Return Login Page"
        )
    }
}

// Created for this screen (image comp)
@Composable
private fun DefaultImage(modifier: Modifier, imageId: Int) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        painter = painterResource(id = imageId),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}

// Created for this screen (text comp)
@Composable
private fun DefaultText(modifier: Modifier, text: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        text = text,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primaryVariant
    )
}