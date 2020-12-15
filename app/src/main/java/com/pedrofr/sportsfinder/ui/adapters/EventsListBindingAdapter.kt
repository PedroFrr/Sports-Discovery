package com.pedrofr.sportsfinder.ui.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.pedrofr.sportsfinder.utils.convertLongToHoursMinutes

@BindingAdapter("startTime")
fun bindStartTime(view: TextView, startDate: Long) {
    val date = convertLongToHoursMinutes(startDate)
    view.text = date
}