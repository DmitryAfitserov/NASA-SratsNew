package com.nasa.nasasratsnew.ui.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nasa.nasasratsnew.R


class ApodListFragment : ListFragment() {

    private lateinit var homeViewModel: ApodViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(ApodViewModel::class.java)
        val root = inflater.inflate(R.layout.list_fragment_apod, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root
    }

    fun getStartData(){


    }


}