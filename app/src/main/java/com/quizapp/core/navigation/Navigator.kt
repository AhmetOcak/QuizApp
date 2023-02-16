package com.quizapp.core.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class Navigator @Inject constructor(){

    var destination: MutableStateFlow<String> = MutableStateFlow(NavScreen.RegisterScreen.route)

    fun navigate(destination: String) {
        this.destination.value = destination
    }
}