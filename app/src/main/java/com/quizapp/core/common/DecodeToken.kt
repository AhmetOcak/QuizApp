package com.quizapp.core.common

import okio.ByteString.Companion.decodeBase64
import org.json.JSONObject

const val USER_ID_KEY = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/authentication"
const val USER_NAME_KEY = "unique_name"

fun getUserIdFromToken(token: String) : String {
    val splitToken = token.split(".")
    val decodedToken = splitToken[1].decodeBase64()?.utf8()
    val jsonDecodedToken = decodedToken?.let { JSONObject(it) }
    return jsonDecodedToken?.getString(USER_ID_KEY) ?: ""
}

fun getUserNameFromToken(token: String) : String {
    val splitToken = token.split(".")
    val decodedToken = splitToken[1].decodeBase64()?.utf8()
    val jsonDecodedToken = decodedToken?.let { JSONObject(it) }
    return jsonDecodedToken?.getString(USER_NAME_KEY) ?: ""
}