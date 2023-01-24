package com.quizapp.core.navigation

import androidx.annotation.DrawableRes
import com.quizapp.R

sealed class BottomNavItem(val route: String, @DrawableRes val icon: Int) {

    object Search : BottomNavItem(
        NavRoutes.search_screen,
        R.drawable.ic_baseline_search
    )

    object Home : BottomNavItem(
        NavRoutes.home_screen,
        R.drawable.ic_baseline_home
    )

    object Profile : BottomNavItem(
        NavRoutes.profile_screen,
        R.drawable.ic_baseline_account_box
    )

    object Leaderboard : BottomNavItem(
        NavRoutes.leaderboard_screen,
        R.drawable.ic_baseline_leaderboard
    )
}