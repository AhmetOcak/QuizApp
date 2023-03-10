package com.quizapp.core.navigation

object NavRoutes {
    const val home_screen = "home_screen"
    const val profile_screen = "profile_screen"
    const val search_screen = "search_screen"
    const val quiz_screen = "${NavNames.quiz_screen}/{quizId}"
    const val sign_in_screen = "login_screen"
    const val register_screen = "register_screen"
    const val forgot_password_screen = "${NavNames.forgot_password_screen}/{email}/{token}"
    const val leaderboard_screen = "leaderboard_screen"
    const val edit_profile_screen = "edit_profile_screen"
    const val quiz_landing_screen = "${NavNames.quiz_landing_screen}/{quizId}/{quizTitle}/{quizDescription}/{quizAuthorUserName}/{quizCreatedDate}/{quizAuthorUserImage}/{categoryName}"
    const val confirm_account_screen = "${NavNames.confirm_account_screen}/{email}/{token}"
    const val create_quiz_screen = "create_quiz_screen"
    const val quiz_result_screen = "${NavNames.quiz_result_screen}/{quizResult}"
    const val update_password_screen = "update_password_screen"
    const val update_profile_screen = "update_profile_screen"
    const val delete_account_screen = "delete_account_screen"
}

object NavNames {
    const val confirm_account_screen = "confirm_account_screen"
    const val forgot_password_screen = "forgot_password_screen"
    const val quiz_landing_screen = "quiz_landing_screen"
    const val quiz_screen = "quiz_screen"
    const val quiz_result_screen = "quiz_result_screen"
}

object BottomNavItems {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Leaderboard,
        BottomNavItem.Profile,
    )
}