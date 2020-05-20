package com.nasa.nasasratsnew.ui.apod

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nasa.nasasratsnew.MainActivity
import com.nasa.nasasratsnew.R
import com.nasa.nasasratsnew.data.ApodData
import com.squareup.picasso.Picasso

class ApodFragment : Fragment(){

    private lateinit var apodViewModel: ApodViewModel
    private lateinit var apod:ApodData
    private val typeMediaImage = "image"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_apod_layout, container, false)


        apodViewModel =
            ViewModelProviders.of(activity!!).get(ApodViewModel::class.java)

        apod = apodViewModel.getApod()

        Log.d("MyCont", "onScroll() firstVisibleItem = ${apod.id}")


        val imageView = root.findViewById<ImageView>(R.id.image_title_apod)
        val titleTextView = root.findViewById<TextView>(R.id.title_text_apod)
        val textTextView = root.findViewById<TextView>(R.id.text_text_apod)

        titleTextView.text = apod.title
        textTextView.text = apod.text

        Picasso.get()
            .load(apod.hdUrl)
              .placeholder(R.drawable.nasa)
            //  .error(R.drawable.user_placeholder_error)
            .into(imageView)

//        if(MainActivity.twoText){
//
//        } else {
//
//        }


        Log.d("MyCont", "isHDImage  @${MainActivity.isHDImage}")
        Log.d("MyCont", "language  @${MainActivity.language}")
        Log.d("MyCont", "twoText  @${MainActivity.twoText}")




        return root
    }
}

