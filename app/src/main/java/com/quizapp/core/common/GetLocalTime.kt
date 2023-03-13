package com.quizapp.core.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
fun getHour(): Int = LocalTime.now().hour

@RequiresApi(Build.VERSION_CODES.O)
fun getMinute(): Int = LocalTime.now().minute

@RequiresApi(Build.VERSION_CODES.O)
fun getSeconds(): Int = LocalTime.now().second