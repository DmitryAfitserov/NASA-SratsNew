package com.nasa.nasasratsnew.controller

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

class ApodControllerImage (){
   // private var listApod:MutableList<Any>
    init {

    }

    fun loadImage():Bitmap{
        var url: URL = URL("https://apod.nasa.gov/apod/image/2005/PorpoiseGalaxy_HubbleFraile_960.jpg")
        var bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
    return  bitmap
    }

}