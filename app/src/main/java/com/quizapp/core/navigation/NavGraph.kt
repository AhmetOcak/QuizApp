package com.quizapp.core.navigation

import android.content.SharedPreferences
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.quizapp.R
import com.quizapp.core.common.removeToken
import com.quizapp.core.ui.component.CustomScaffold
import com.quizapp.core.ui.theme.Black
import com.quizapp.core.ui.theme.TransparentWhite
import com.quizapp.core.ui.theme.WhiteSmoke
import com.quizapp.presentation.confirm_account.ConfirmAccountScreen
import com.quizapp.presentation.contact_us.ContactUsScreen
import com.quizapp.presentation.create_quiz.CreateQuizScreen
import com.quizapp.presentation.edit_profile.EditProfileScreen
import com.quizapp.presentation.search.SearchScreen
import com.quizapp.presentation.reset_password.ResetPasswordScreen
import com.quizapp.presentation.home.HomeScreen
import com.quizapp.presentation.leaderboard.LeaderboardScreen
import com.quizapp.presentation.profile.ProfileScreen
import com.quizapp.presentation.quiz.QuizScreen
import com.quizapp.presentation.quiz_landing.QuizLandingScreen
import com.quizapp.presentation.register.RegisterScreen
import com.quizapp.presentation.signin.SignInScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = NavScreen.SearchScreen.route,
    sharedPreferences: SharedPreferences
) {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val destination by Navigator.destination.collectAsState()

    LaunchedEffect(destination) {
        if (destination.isBlank()) {
            navController.navigate(startDestination)
        } else if (navController.currentDestination?.route != destination) {
            navController.navigate(destination)
        }
    }

    CustomScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (currentRoute == NavScreen.ProfileScreen.route) {
                ProfileScreenTopAppBar(
                    logOut = {
                        with(sharedPreferences.edit()) {
                            removeToken()
                        }
                        Navigator.navigate(NavScreen.SignInScreen.route) {
                            popUpTo(0)
                        }
                    }
                )
            }
        },
        content = {
            AnimatedNavHost(
                modifier = modifier.padding(it),
                navController = navController,
                startDestination = startDestination,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                }
            ) {
                composable(route = NavScreen.SearchScreen.route) {
                    SearchScreen()
                }
                composable(route = NavScreen.HomeScreen.route) {
                    HomeScreen()
                }
                composable(route = NavScreen.ProfileScreen.route) {
                    ProfileScreen()
                }
                composable(route = NavScreen.SignInScreen.route) {
                    SignInScreen()
                }
                composable(route = NavScreen.RegisterScreen.route) {
                    RegisterScreen()
                }
                composable(
                    route = NavScreen.ForgotPasswordScreen.route,
                    arguments = listOf(
                        navArgument("email") { type = NavType.StringType },
                        navArgument("token") { type = NavType.StringType }
                    )
                ) {
                    ResetPasswordScreen()
                }
                composable(
                    route = NavScreen.QuizScreen.route,
                    arguments = listOf(
                        navArgument("quizId") { type = NavType.StringType }
                    )
                ) {
                    QuizScreen()
                }
                composable(route = NavScreen.LeaderboardScreen.route) {
                    LeaderboardScreen()
                }
                composable(
                    route = NavScreen.QuizLandingScreen.route,
                    arguments = listOf(
                        navArgument("quizId") { type = NavType.StringType },
                        navArgument("quizTitle") { type = NavType.StringType },
                        navArgument("quizDescription") { type = NavType.StringType },
                        navArgument("quizAuthorUserName") { type = NavType.StringType },
                        navArgument("quizCreatedDate") { type = NavType.StringType },
                        navArgument("quizAuthorUserImage") { type = NavType.StringType },
                        navArgument("categoryName") { type = NavType.StringType }
                    )
                ) {
                    QuizLandingScreen()
                }
                composable(route = NavScreen.EditProfileScreen.route) {
                    EditProfileScreen()
                }
                composable(route = NavScreen.ContactUsScreen.route) {
                    ContactUsScreen()
                }
                composable(
                    route = NavScreen.ConfirmAccountScreen.route,
                    arguments = listOf(
                        navArgument("email") { type = NavType.StringType },
                        navArgument("token") { type = NavType.StringType }
                    )
                ) {
                    ConfirmAccountScreen()
                }
                composable(route = NavScreen.CreateQuizScreen.route) {
                    CreateQuizScreen()
                }
            }
            BottomAppBar(
                modifier = modifier,
                currentRoute = currentRoute,
                navController = navController
            )
        }
    )
}

@Composable
private fun ProfileScreenTopAppBar(logOut: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = {
                    Navigator.navigate(NavScreen.EditProfileScreen.route) {}
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_edit),
                    contentDescription = null,
                    tint = WhiteSmoke
                )
            }
        },
        actions = {
            IconButton(onClick = logOut) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = WhiteSmoke
                )
            }
        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
private fun BottomAppBar(modifier: Modifier, currentRoute: String?, navController: NavController) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        BottomNavItems.items.forEach { navItem ->
            if (navItem.route == currentRoute) {
                BottomAppBar(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .navigationBarsPadding()
                        .clip(shape = RoundedCornerShape(20))
                        .border(
                            border = BorderStroke(width = 1.dp, color = Black),
                            shape = RoundedCornerShape(20)
                        ),
                    backgroundColor = TransparentWhite,
                    elevation = 0.dp
                ) {
                    BottomAppBarContent(
                        currentRoute = currentRoute,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.BottomAppBarContent(currentRoute: String?, navController: NavController) {
    BottomNavItems.items.forEachIndexed { index, screen ->
        BottomNavigationItem(
            selected = currentRoute == screen.route,
            selectedContentColor = Black,
            unselectedContentColor = Color.Gray,
            onClick = {
                if (currentRoute == screen.route) {
                    return@BottomNavigationItem
                }

                if (currentRoute != screen.route) {
                    navController.navigate(screen.route) {
                        NavScreen.HomeScreen.route.let {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = screen.icon),
                    contentDescription = null,
                )
            }
        )
    }
}