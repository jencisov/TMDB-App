package com.jencisov.tmdb.extensions

import android.widget.ImageView
import com.jencisov.tmdb.BuildConfig
import com.jencisov.tmdb.R
import com.squareup.picasso.Picasso

fun ImageView.loadPoster(imagePath: String?) {
    if (imagePath == null) {
        return
    }
    val url = BuildConfig.POSTER_LITTLE_BASE_URL + imagePath
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.place_holder)
        .error(R.drawable.place_holder)
        .into(this);
}