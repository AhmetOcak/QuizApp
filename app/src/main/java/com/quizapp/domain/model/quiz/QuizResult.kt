package com.quizapp.domain.model.quiz

import android.net.Uri
import com.google.gson.Gson

data class QuizResult(
    val quizResult: QuizResultDetail
) {
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}

data class QuizResultDetail(
    val quizId: String,
    val title: String,
    val description: String,
    val correctAnswerCount: Int,
    val score: Int,
    val questions: ArrayList<QuestionsResultDetail>
)

data class QuestionsResultDetail(
    val title: String,
    val description: String,
    val selectedOptionDescription: String,
    val isCorrect: Boolean
)