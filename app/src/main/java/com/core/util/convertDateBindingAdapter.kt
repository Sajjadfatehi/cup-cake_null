package com.core.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

@BindingAdapter(value = ["bind:date"], requireAll = true)
fun dateCalculator(textView: TextView, date: String) {

    if (!date.isNullOrEmpty()) {

        val milliSecondOfADay: Long = 86400000

        val format = SimpleDateFormat("yyyy-MM-dd")

        val firstDate = date.substring(0, 10)
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

        val x = format.parse(firstDate)
        val y = format.parse(currentDate)

        val diff = x.time - y.time

        val countOfDays = abs(diff) / milliSecondOfADay


        var text = when (countOfDays) {
            0L -> "امروز"
            in 1..6 -> "$countOfDays روز گذشته "
            in 7..30 -> "${countOfDays / 7} هفته گذشته "
            in 30..360 -> "${countOfDays / 30} ماه پیش "
            in 360..Int.MAX_VALUE -> "${countOfDays / 360} سال گذشته "
            else -> ""
        }

        textView.text = text
    }

}

