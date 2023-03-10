package com.quizapp.core.navigation

sealed class NavScreen(val route: String) {
    object SignInScreen : NavScreen(route = NavRoutes.sign_in_screen)
    object RegisterScreen : NavScreen(route = NavRoutes.register_screen)
    object ForgotPasswordScreen : NavScreen(route = NavRoutes.forgot_password_screen)
    object SearchScreen : NavScreen(route = NavRoutes.search_screen)
    object HomeScreen : NavScreen(route = NavRoutes.home_screen)
    object ProfileScreen : NavScreen(route = NavRoutes.profile_screen)
    object QuizScreen : NavScreen(route = NavRoutes.quiz_screen)
    object LeaderboardScreen : NavScreen(route = NavRoutes.leaderboard_screen)
    object EditProfileScreen : NavScreen(route = NavRoutes.edit_profile_screen)
    object QuizLandingScreen : NavScreen(route = NavRoutes.quiz_landing_screen)
    object ConfirmAccountScreen : NavScreen(route = NavRoutes.confirm_account_screen)
    object CreateQuizScreen : NavScreen(route = NavRoutes.create_quiz_screen)
    object QuizResultScreen : NavScreen(route = NavRoutes.quiz_result_screen)
    object UpdatePasswordScreen : NavScreen(route = NavRoutes.update_password_screen)
    object UpdateProfileScreen : NavScreen(route = NavRoutes.update_profile_screen)
    object DeleteAccountScreen : NavScreen(route = NavRoutes.delete_account_screen)
}