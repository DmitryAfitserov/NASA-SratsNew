package com.nasa.nasasratsnew.controller

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.nasa.nasasratsnew.data.ApodData
import com.squareup.picasso.Picasso


class ApodControllerImage (private var context: Context, private var listApod:MutableList<Any?>, var callbackToList: ()-> Unit){


    private val callbackToController = {id:Int, isError:Boolean, bitmap:Bitmap? -> response(id, isError, bitmap)}
  //  var image = ImageView(context)

    fun loadImages(){

        for(i in 0..listApod.size - 2){

            (listApod[i] as ApodData).image?.let {



            } ?: run {
             //   val target = PicassoTarget(i, callbackToController)
             //   val trans = CustomTransformation(i, callbackToController)
                (listApod[i] as ApodData).image = ImageView(context)
                Picasso.get()
                    .load((listApod[i] as ApodData).url)
                    //  .placeholder(R.drawable.user_placeholder)
                    //  .error(R.drawable.user_placeholder_error)
                   // .transform(trans)
                    .into((listApod[i] as ApodData).image)

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