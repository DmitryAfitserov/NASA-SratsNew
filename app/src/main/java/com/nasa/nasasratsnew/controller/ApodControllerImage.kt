package com.nasa.nasasratsnew.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import com.nasa.nasasratsnew.data.ApodData
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.net.URL

class ApodControllerImage (private var context: Context, private var listApod:MutableList<Any?>, var callbackToList: ()-> Unit){


    private val callbackToController = {id:Int, isError:Boolean, bitmap:Bitmap? -> response(id, isError, bitmap)}

    fun loadImages(){

        for(i in 0..listApod.size - 2){

            (listApod[i] as ApodData).bitmap?.let {



            } ?: run {

                Picasso.with(context)
                    .load((listApod[i] as ApodData).url)
                    //  .placeholder(R.drawable.user_placeholder)
                    //  .error(R.drawable.user_placeholder_error)
                    .into(PicassoTarget(i, callbackToController))

            }



        }



    }

    private fun response(id:Int, isError:Boolean, bitmap:Bitmap?){
        Log.d("MyCont", "response")
        if(isError){
            Log.d("MyCont", "isError")
        } else {

            (listApod[id] as ApodData).bitmap = bitmap
            callbackToList.invoke()
            Log.d("MyCont", "bitmap added in apod, id = $id")
        }
    }

}