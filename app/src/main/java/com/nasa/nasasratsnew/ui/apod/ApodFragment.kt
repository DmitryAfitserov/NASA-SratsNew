package com.nasa.nasasratsnew.ui.apod

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.nasa.nasasratsnew.MainActivity
import com.nasa.nasasratsnew.R

class ApodFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_apod_layout, container, false)
        val imageView = root.findViewById<ImageView>(R.id.imageView)
     //   var image = ImageView(context)


        Log.d("MyCont", "isHDImage  @${MainActivity.isHDImage}")



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

