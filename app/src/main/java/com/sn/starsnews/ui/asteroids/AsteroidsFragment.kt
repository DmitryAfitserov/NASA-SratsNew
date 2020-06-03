package com.sn.starsnews.ui.asteroids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sn.starsnews.R

class AsteroidsFragment : Fragment() {

    private lateinit var galleryViewModel: AsteroidsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(AsteroidsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_asteroids, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(this, Observer {
            textView.text = context?.getString(R.string.developing)
        })
        return root
    }
}