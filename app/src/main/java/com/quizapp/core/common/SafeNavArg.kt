package com.quizapp.core.common

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// For the fix slash character problem

fun encodeForSafe(string: String): String =
    URLEncoder.encode(string, StandardCharsets.UTF_8.toString())
