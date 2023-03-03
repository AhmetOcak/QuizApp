package com.quizapp.core.navigation

import com.google.gson.Gson
import com.quizapp.core.common.JsonNavType
import com.quizapp.domain.model.quiz.QuizResult

class QuizResultArgType : JsonNavType<QuizResult>() {

    override fun fromJsonParse(value: String): QuizResult = Gson().fromJson(value, QuizResult::class.java)

    override fun QuizResult.getJsonParse(): String = Gson().toJson(this)
}