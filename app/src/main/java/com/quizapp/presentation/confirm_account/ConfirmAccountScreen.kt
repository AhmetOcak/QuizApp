package com.quizapp.presentation.confirm_account

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.R
import com.quizapp.core.navigation.Navigator
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.OnBackPressed
import com.quizapp.core.ui.component.OutBtnCustom
import com.quizapp.presentation.signin.SignInViewModel
import com.quizapp.presentation.utils.Messages

@Composable
fun ConfirmAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: ConfirmAccountViewModel = hiltViewModel()
) {

    val confirmAccountState by viewModel.confirmAccountState.collectAsState()
    val activity = LocalContext.current as Activity

    OnBackPressed(activity = activity)

    ConfirmAccountScreenContent(
        modifier = modifier,
        onClick = { viewModel.confirmAccount() },
        confirmAccountState = confirmAccountState,
        activity = activity,
        onReset = { viewModel.resetConfirmAccountState() }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ConfirmAccountScreenContent(
    modifier: Modifier,
    onClick: () -> Unit,
    confirmAccountState: ConfirmAccountState,
    activity: Activity,
    onReset: () -> Unit
) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (confirmAccountState) {
                is ConfirmAccountState.Nothing -> {
                    ImageSection(modifier = modifier)
                    ContentSection(modifier = modifier)
                    ActivateButtonSection(modifier = modifier, onClick = onClick)
                }
                is ConfirmAccountState.Loading -> {
                    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CustomLoadingSpinner()
                    }
                }
                is ConfirmAccountState.Success -> {
                    ShowMessage(message = Messages.ACCOUNT_VERIFIED)
                    Navigator.resetDestination()
                    activity.finish()
                }
                is ConfirmAccountState.Error -> {
                    ShowMessage(
                        message = confirmAccountState.errorMessage,
                        confirmAccountState = confirmAccountState,
                        onReset = onReset
                    )
                }
            }
        }
    }
}

@Composable
private fun ImageSection(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(180.dp)
            .clip(CircleShape)
            .background(color = Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = modifier.size(128.dp),
            painter = painterResource(id = R.drawable.ic_baseline_check),
            contentDescription = null,
            tint = MaterialTheme.colors.background
        )
    }
}

@Composable
private fun ContentSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Account Activation",
            style = MaterialTheme.typography.h1.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
        Text(
            modifier = modifier.padding(top = 16.dp),
            text = "Hi. Welcome to QuizApp, to activate your account, just click the button below.",
            style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.primaryVariant),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ActivateButtonSection(modifier: Modifier, onClick: () -> Unit) {
    OutBtnCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = onClick,
        buttonText = "Activate Account"
    )
}

@Composable
private fun ShowMessage(
    message: String,
    confirmAccountState: ConfirmAccountState = ConfirmAccountState.Nothing,
    onReset: () -> Unit = {}
) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_LONG
    ).show()
    if (confirmAccountState is ConfirmAccountState.Error) {
        onReset()
    }
}