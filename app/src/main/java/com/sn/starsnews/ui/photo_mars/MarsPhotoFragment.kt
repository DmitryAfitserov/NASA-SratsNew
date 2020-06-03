package com.sn.starsnews.ui.photo_mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sn.starsnews.R

class MarsPhotoFragment : Fragment() {

    private lateinit var slideshowViewModel: MarsPhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(MarsPhotoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mars_photo, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        slideshowViewModel.text.observe(this, Observer {
            textView.text = context?.getString(R.string.developing)
        })
        return root
    }
}