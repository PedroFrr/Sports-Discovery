package com.pedrofr.sportsfinder.ui.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.pedrofr.sportsfinder.utils.convertHourMinutes

@BindingAdapter("startTime")
fun bindStartTime(view: TextView, startDate: Long) {
    val date = convertHourMinutes(startDate)
    view.text = date
}