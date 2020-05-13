package com.nasa.nasasratsnew.controller
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class PicassoTarget(var id:Int, var  callbackToController: (id:Int, isError:Boolean, bitmap:Bitmap?) -> Unit) : Target {

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        Log.d("MyCont", "onPrepareLoad()")

    }


    override fun onBitmapFailed(errorDrawable: Drawable?) {
        Log.d("MyCont", "onBitmapFailed()")
        callbackToController.invoke(id, true, null)

    }


    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        Log.d("MyCont", "onBitmapLoaded()")
        callbackToController.invoke(id, false, bitmap)
    }
}