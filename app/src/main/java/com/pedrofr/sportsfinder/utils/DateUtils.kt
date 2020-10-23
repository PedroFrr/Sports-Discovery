package com.pedrofr.sportsfinder.utils

import android.text.format.DateFormat
import java.util.*

fun convertToDayMonth(date: Long): String = DateFormat.format("dd-MM-yyyy", Date(date * 1000L)).toString()

fun convertHourMinutes(date: Long): String = DateFormat.format("HH:mm", Date(date * 1000L)).toString()