package com.quizapp.data.datasource.remote.option.entity

import com.google.gson.annotations.SerializedName

data class OptionsDto(
    @SerializedName("options")
    val options: ArrayList<OptionDto>
)

data class OptionDto(
    @SerializedName("optionId")
    val optionId: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("isAnswer")
    val isAnswer: Boolean
)
