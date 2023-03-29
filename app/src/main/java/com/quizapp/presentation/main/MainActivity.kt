package com.quizapp.presentation.main

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.quizapp.core.common.ImageLoader
import com.quizapp.core.common.getToken
import com.quizapp.core.navigation.NavGraph
import com.quizapp.core.navigation.NavNames
import com.quizapp.core.navigation.NavScreen
import com.quizapp.core.navigation.Navigator
import com.quizapp.core.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("MainActivity.kt", "Permission Granted")
        } else {
            Log.i("MainActivity.kt", "Permission Denied")
        }
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission Granted
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                // Additional rationale should be displayed
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            else -> {
                // Permission has been asked yet
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImageLoader.load(context = applicationContext)

        requestPermission()

        setContent {
            QuizAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (sharedPreferences.getToken().isNullOrEmpty()) {
                        NavGraph()
                    } else {
                        NavGraph(startDestination = NavScreen.HomeScreen.route,)
                    }
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

        appLinkData?.lastPathSegment?.also { recipeId ->
            Log.e("recipe id = ", recipeId)

            if (recipeId == "ConfirmMail") {
                handleConfirmMailIntent(recipeId, appLinkData)
            } else if (recipeId == "ResetPassword") {
                handlePasswordResetMailIntent(recipeId, appLinkData)
            }
        }
    }

    private fun handleConfirmMailIntent(recipeId: String, appLinkData: Uri) {
        var email: String?
        var token: String?

        Uri.parse("https://localhost:7250/api/Auth/ConfirmMail")
            .buildUpon()
            .appendPath(recipeId)
            .build().also {
                email = appLinkData.getQueryParameter("email")
                token = appLinkData.getQueryParameter("token")

                val encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString())

                lifecycleScope.launch() {
                    Navigator.navigate("${NavNames.confirm_account_screen}/${email}/${encodedToken}") {}
                }
            }
    }

    private fun handlePasswordResetMailIntent(recipeId: String, appLinkData: Uri) {
        var token: String?
        var email: String?

        Uri.parse("https://localhost:7250/api/auth/ResetPassword")
            .buildUpon()
            .appendPath(recipeId)
            .build().also {
                token = appLinkData.getQueryParameter("token")
                email = appLinkData.getQueryParameter("email")

                val encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString())

                lifecycleScope.launch() {
                    Navigator.navigate("${NavNames.forgot_password_screen}/${email}/${encodedToken}") {}
                }
            }
    }

}