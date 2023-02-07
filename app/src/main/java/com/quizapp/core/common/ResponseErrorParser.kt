package com.quizapp.core.common

import org.json.JSONObject
import retrofit2.HttpException

fun HttpException.getErrorMessage(): String? {
    return response()?.errorBody()?.source()?.buffer?.snapshot()?.utf8()?.let {
        JSONObject(it).getString("error")
    }
}