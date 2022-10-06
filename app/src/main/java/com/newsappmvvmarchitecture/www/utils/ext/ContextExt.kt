package com.newsappmvvmarchitecture.www.utils.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.widget.Toast

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
