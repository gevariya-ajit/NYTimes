package nytimes.example.app.utils

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import nytimes.example.app.R

fun ImageView.loadCircleAvatar(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide
        .with(this)
        .load(url)
        .circleCrop()
        .error(R.drawable.ic_person)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(circularProgressDrawable)
        .into(this)
}

fun ImageView.loadCoverImage(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide
        .with(this)
        .load(url)
        .centerCrop()
        .error(R.drawable.ic_person)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(circularProgressDrawable)
        .into(this)
}