package com.quizapp.data.datasource.remote.question.entity

import com.google.gson.annotations.SerializedName

data class QuestionListDto(
    @SerializedName("questions")
    val questionList: ArrayList<QuestionDto>
)

data class QuestionDto(
    @SerializedName("questionId")
    val questionId: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("descripton")
    val description: String,

    @SerializedName("options")
    val options: ArrayList<OptionDto>
)

data class OptionDto(
    @SerializedName("optionId")
    val optionId: String,

    @SerializedName("description")
    val description: String
)