package com.example.sneakers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object CommonUtils {
    fun loadImage(context: Context, url: String?, image: ImageView) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.shoes)
            .into(image)
    }



}