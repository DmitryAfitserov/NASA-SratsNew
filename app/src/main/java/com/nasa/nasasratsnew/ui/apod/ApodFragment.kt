package com.nasa.nasasratsnew.ui.apod

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.nasa.nasasratsnew.R
import com.nasa.nasasratsnew.controller.ApodControllerImage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_apod_layout.*
import java.net.URL
import kotlin.concurrent.thread

class ApodFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_apod_layout, container, false)
        val imageView = root.findViewById<ImageView>(R.id.imageView)
     //   var image = ImageView(context)


        var target = object: Target{
            override fun onBitmapFailed(errorDrawable: Drawable?) {
                Log.d("MyCont", "onBitmapFailed()")
               // TO,DO("not implemented") To change body of created functions use File | Settings | File Templates.
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.d("MyCont", "onPrepareLoad()")
             //  TO,DO("not implemented") To change body of created functions use File | Settings | File Templates.
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                imageView.setImageBitmap(bitmap)
                Log.d("MyCont", "onBitmapLoaded()")
                //  TO,DO("not implemented") To change body of created functions use File | Settings | File Templates.
            }
        }


        Picasso.with(context)
            .load("https://apod.nasa.gov/apod/image/2005/PorpoiseGalaxy_HubbleFraile_960.jpg")
          //  .placeholder(R.drawable.user_placeholder)
          //  .error(R.drawable.user_placeholder_error)
            .into(target)



  //      var image:Bitmap? = null

//        thread(start = true) {
//            val url: URL = URL("https://apod.nasa.gov/apod/image/2005/PorpoiseGalaxy_HubbleFraile_960.jpg")
//            image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//         //   imageView.setImageBitmap(image)
//          //  println("running from thread(): ${Thread.currentThread()}")
//        }
//        imageView.setImageBitmap(image)

        return root
    }
}

