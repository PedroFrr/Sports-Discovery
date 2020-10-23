package com.pedrofr.sportsfinder.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("startTime")
fun bindStartTime(view: TextView, startDate: Date) {
    val date = DateFormat.getDateInstance(DateFormat.SHORT).format(startDate)
    view.text = date
}
