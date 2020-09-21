
package com.core.util

import android.graphics.Color
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.anull.R
import com.google.android.material.button.MaterialButton


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


@BindingAdapter(value = ["bind:isFollowing"], requireAll = true)
fun handleBackGroundOfFollowButton(view: View, isFollow: Boolean) {

    view as MaterialButton
    if (isFollow){
        view.setBackgroundColor(Color.parseColor("#177C1C"))
        view.text="دنبال میکنید"
    }
    else{
        view.setBackgroundColor(Color.parseColor("#286de6"))
        view.text="دنبال کردن"
    }


}
