package com.example.myarchitecture.shared.helpers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object CurrencyBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:glideSrc")
    fun setImageUrl(view: ImageView, url: String?) {
        Glide.with(view.context).load(url).apply { RequestOptions().centerCrop() }.into(view)
    }
}