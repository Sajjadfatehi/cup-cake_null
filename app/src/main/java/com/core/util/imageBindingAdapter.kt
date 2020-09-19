
package com.core.util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.anull.R


@BindingAdapter(value = ["bind:url"], requireAll = true)
fun loadImage(view: View, url: String?) {
    view as AppCompatImageView
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url)
            .transform(CircleCrop()).into(view)
    } else {
        Glide.with(view.context)
            .clear(view)
        // remove the placeholder (optional); read comments below
        view.setImageDrawable(null)
    }


}

@BindingAdapter(value = ["bind:isFavorites"], requireAll = true)
fun handleBookMarkImage(view: View, isFavorites: Boolean) {

    view as AppCompatImageView
    if (isFavorites) {
        loadImage(view, R.drawable.ic_bookmark_blue)
    } else {
        loadImage(view, R.drawable.ic_bookmark)

    }

}

fun loadImage(imageView: AppCompatImageView, rec: Int) {
    Glide.with(imageView.context).load(rec).into(imageView)
}
