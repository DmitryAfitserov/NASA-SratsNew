package com.nasa.nasasratsnew.controller

import android.graphics.Bitmap
import android.util.Log
import com.squareup.picasso.Transformation

class CustomTransformation(var id:Int, var  callbackToController: (id:Int, isError:Boolean, bitmap:Bitmap?) -> Unit)  :Transformation{

    override fun key(): String {
      //
        return ""
    }

    override fun transform(source: Bitmap?): Bitmap {
     //
        Log.d("MyCont", "transform = $id")
        callbackToController.invoke(id, false, source)
        return source!!
    }
}