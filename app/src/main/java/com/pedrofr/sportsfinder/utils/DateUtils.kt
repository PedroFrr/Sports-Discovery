package com.pedrofr.sportsfinder.utils

import android.text.format.DateFormat
import java.util.*

fun convertLongToDayMonthYear(date: Long): String = DateFormat.format("dd-MM-yyyy", Date(date * 1000L)).toString()

fun convertLongToHoursMinutes(date: Long): String = DateFormat.format("HH:mm", Date(date * 1000L)).toString()