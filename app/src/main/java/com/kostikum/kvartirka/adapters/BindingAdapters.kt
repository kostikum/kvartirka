package com.kostikum.kvartirka.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kostikum.kvartirka.R
import com.kostikum.kvartirka.entity.BuildingType

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url.isNullOrEmpty()) imageView.visibility = View.GONE
    else {
        imageView.visibility = View.VISIBLE
        Glide.with(imageView.context).load(url).into(imageView)
    }
}

@BindingAdapter("setBuildingTypeText")
fun setText(textView: TextView, text: String?) {
    textView.text = when (text) {
        "flat" -> BuildingType.FLAT.getResName()
        "cottage" -> BuildingType.COTTAGE.getResName()
        else -> BuildingType.UNKNOWN.getResName()
    }
}

@BindingAdapter("dayPrice", "nightPrice", "hourPrice")
fun concatPriceString(
    textView: TextView,
    dayPrice: Int,
    nightPrice: Int,
    hourPrice: Int
) {
    textView.text = buildString {
        if (dayPrice != 0) {
            append(textView.context.getString(R.string.day_price))
            append(" ")
            append(dayPrice)
        }
        if (nightPrice != 0) {
            append(textView.context.getString(R.string.night_price))
            append(" ")
            append(nightPrice)
        }
        if (hourPrice != 0) {
            append(textView.context.getString(R.string.hour_price))
            append(" ")
            append(hourPrice)
        }
    }
}
