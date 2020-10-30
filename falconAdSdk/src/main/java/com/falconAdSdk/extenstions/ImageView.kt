package com.falconAdSdk.extenstions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadImageWithGif(url: String?) {
    if (url != null) {
        val extension = url.split(".").last()
        if(extension.toLowerCase()=="gif"){
            Glide.with(context)
                .asGif()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }else{
            Glide.with(context)
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }

    }
}