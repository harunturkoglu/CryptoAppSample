package com.harunturkoglu.cryptoappsample.utils

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.harunturkoglu.cryptoappsample.ext.orElse
import com.harunturkoglu.cryptoappsample.presentation.ui.common.component.CurrencyStateComponent


@BindingAdapter("textColorRes")
fun TextView.textColorRes(@ColorRes resId: Int) {
    setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, resId)))
}

@BindingAdapter("isVisible")
fun View.setVisibleState(visible: Boolean) {
    isVisible = visible
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("currencyTitle")
fun setCurrencyTitle(view: CurrencyStateComponent, title: String?){
  view.setTitleText(title.orElse())
}

@BindingAdapter("currencyDescription")
fun setCurrencyDescription(view: CurrencyStateComponent, description: String?){
    view.setDescriptionText(description.orElse())
}