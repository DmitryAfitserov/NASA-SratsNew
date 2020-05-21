package com.nasa.nasasratsnew.ui.apod

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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



        val imageView = root.findViewById<ImageView>(R.id.image_title_apod) // work with image

        if(apod.typeMedia == typeMediaImage){
            val url = if(MainActivity.isHDImage) apod.hdUrl else apod.url
            var placeholder = creatorDrawable()


            placeholder?.let {
                picassoLoad(placeholder, imageView, url!!)
                Log.d("MyCont", "used placeholder")
            } ?: run {
                picassoLoad(R.drawable.nasa ,imageView, url!!)
            }
        } else {    // work with video

            // create intent for video
        }

        // work with text

        val titleTextViewTranslate = root.findViewById<TextView>(R.id.title_text_apod_translate)
        val textTextViewTranslate = root.findViewById<TextView>(R.id.text_text_apod_translate)

        if(MainActivity.language == MainActivity.languageDefault){
            titleTextViewTranslate.text = apod.title
            textTextViewTranslate.text = apod.text
        } else {
            apod.textTranslate?.let {
                apod.titleTranslate?.let {
                    titleTextViewTranslate.text = apod.titleTranslate
                    textTextViewTranslate.text = apod.textTranslate
                }
            }
        }

        if(MainActivity.twoText){
            val linearLayout = root.findViewById<TextView>(R.id.linear_layout_translate)
            linearLayout.visibility = View.VISIBLE
            val titleTextView = root.findViewById<TextView>(R.id.title_text_apod)
            val textTextView = root.findViewById<TextView>(R.id.text_text_apod)

            titleTextView.text = apod.title
            textTextView.text = apod.text

        }

        return root
    }


    private fun picassoLoad(res:Int, imageView: ImageView, url:String){
        Picasso.get()
            .load(url)
            .placeholder(res)
            //  .error(R.drawable.user_placeholder_error)
            .into(imageView)
    }

    private fun picassoLoad(drawable: Drawable, imageView: ImageView, url:String){
        Picasso.get()
            .load(url)
            .placeholder(drawable)
            //  .error(R.drawable.user_placeholder_error)
            .into(imageView)
    }

    private fun creatorDrawable(): Drawable? {
        apod.height?.let {
            apod.width?.let {
                val bitmap = Bitmap.createBitmap(apod.width!!, apod.height!!, Bitmap.Config.ARGB_8888)
                Log.d("MyCont", "Drawable created")
                return BitmapDrawable(resources, bitmap)
            }
        }
        return null
    }



}

