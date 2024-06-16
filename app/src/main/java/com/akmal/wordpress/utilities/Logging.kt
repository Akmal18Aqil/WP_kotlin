package com.akmal.wordpress.utilities

import android.util.Log

fun logError(TAG:String,message:String){
    if(message.isEmpty()){
        Log.e(TAG,"Message is Empty")
        return
    }
    Log.e(TAG,message)
}