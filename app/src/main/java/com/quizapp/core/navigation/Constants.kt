package com.quizapp.core.navigation

object NavRoutes {
    const val home_screen = "home_screen"
    const val profile_screen = "profile_screen"
    const val search_screen = "search_screen"
    const val quiz_screen = "${NavNames.quiz_screen}/{${QuizScreenArgs.QUIZ_ID}}"
    const val sign_in_screen = "login_screen"
    const val register_screen = "register_screen"
    const val forgot_password_screen =
        "${NavNames.forgot_password_screen}/{${ForgotPasswordScreenArgs.EMAIL}}/{${ForgotPasswordScreenArgs.TOKEN}}"
    const val leaderboard_screen = "leaderboard_screen"
    const val edit_profile_screen =
        "${NavNames.edit_profile_screen}/{${EditProfileScreenArgs.FIRST_NAME}}/" +
                "{${EditProfileScreenArgs.LAST_NAME}}/{${EditProfileScreenArgs.USER_NAME}}/{${EditProfileScreenArgs.USER_PROFILE_IMG}}"

    const val quiz_landing_screen =
        "${NavNames.quiz_landing_screen}/{${QuizLandingScreenArgs.QUIZ_ID}}/" +
                "{${QuizLandingScreenArgs.QUIZ_TITLE}}/{${QuizLandingScreenArgs.QUIZ_DESCR}}/{${QuizLandingScreenArgs.QUIZ_AUTHOR_NAME}}/" +
                "{${QuizLandingScreenArgs.QUIZ_CREATED_DATE}}/{${QuizLandingScreenArgs.QUIZ_AUTHOR_IMG}}/" +
                "{${QuizLandingScreenArgs.QUIZ_CATEGORY}}"

    const val confirm_account_screen = "${NavNames.confirm_account_screen}/{${ConfirmAccountScreenArgs.EMAIL}}/{${ConfirmAccountScreenArgs.TOKEN}}"
    const val create_quiz_screen = "create_quiz_screen"
    const val quiz_result_screen =
        "${NavNames.quiz_result_screen}/{${QuizResultScreenArgs.QUIZ_RESULT}}/{${QuizResultScreenArgs.QUIZ_START_HOUR}}/" +
                "{${QuizResultScreenArgs.QUIZ_START_MIN}}/{${QuizResultScreenArgs.QUIZ_START_SEC}}"
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
    const val edit_profile_screen = "edit_profile_screen"
}

object BottomNavItems {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.CreateQuiz,
        BottomNavItem.Leaderboard,
        BottomNavItem.Profile
    )
}

object ForgotPasswordScreenArgs {
    const val EMAIL = "email"
    const val TOKEN = "token"
}

object QuizScreenArgs {
    const val QUIZ_ID = "quizId"
}

object QuizLandingScreenArgs {
    const val QUIZ_ID = "quizId"
    const val QUIZ_TITLE = "quizTitle"
    const val QUIZ_DESCR = "quizDescription"
    const val QUIZ_AUTHOR_NAME = "quizAuthorUserName"
    const val QUIZ_CREATED_DATE = "quizCreatedDate"
    const val QUIZ_AUTHOR_IMG = "quizAuthorUserImage"
    const val QUIZ_CATEGORY = "categoryName"
}

object EditProfileScreenArgs {
    const val FIRST_NAME = "firstName"
    const val LAST_NAME = "lastName"
    const val USER_NAME = "userName"
    const val USER_PROFILE_IMG = "userProfileImage"
}

object ConfirmAccountScreenArgs {
    const val EMAIL = "email"
    const val TOKEN = "token"
}

object QuizResultScreenArgs {
    const val QUIZ_RESULT = "quizResult"
    const val QUIZ_START_HOUR = "quizStartHour"
    const val QUIZ_START_MIN = "quizStartMinute"
    const val QUIZ_START_SEC = "quizStartSeconds"
}