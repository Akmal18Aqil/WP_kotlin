package com.akmal.wordpress.utilities

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

fun loadImageGlide(url: String, imageView: ImageView, context: Context){
    Glide.with(context)
        .load(url)
        .transition(withCrossFade())
        .thumbnail(0.5f)
        .into(imageView)
}
fun showMessage(context: Context, message:String){
    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
}