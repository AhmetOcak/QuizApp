package com.quizapp.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.quizapp.core.navigation.NavGraph
import com.quizapp.core.navigation.NavNames
import com.quizapp.core.navigation.NavScreen
import com.quizapp.core.navigation.Navigator
import com.quizapp.core.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavGraph(
                        startDestination = NavScreen.SignInScreen.route,
                        navigator = navigator
                    )
                }
            }
        }

        handleIntent(intent = intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent = intent)
    }

    private fun handleIntent(intent: Intent) {
        val appLinkData = intent.data

        var email: String? = null
        var token: String? = null

        appLinkData?.lastPathSegment?.also { recipeId ->
            Uri.parse("https://localhost:7250/api/Auth/ConfirmMail")
                .buildUpon()
                .appendPath(recipeId)
                .build().also {
                    email = appLinkData.getQueryParameter("email")
                    token = appLinkData.getQueryParameter("token")
                    Log.e("email", email.toString())
                    Log.e("token", token.toString())

                    val encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString())
                    Log.e("encoded token", encodedToken)

                    lifecycleScope.launch() {
                        navigator.navigate("${NavNames.confirm_account_screen}/${email}/${encodedToken}")
                    }
                }
        }
    }

}