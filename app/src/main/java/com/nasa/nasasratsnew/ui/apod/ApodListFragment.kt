package com.nasa.nasasratsnew.ui.apod

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nasa.nasasratsnew.R
import com.nasa.nasasratsnew.controller.ApodController
import kotlinx.android.synthetic.main.list_fragment_apod.*


class ApodListFragment : ListFragment() {

    private lateinit var controller:ApodController
    private lateinit var apodViewModel: ApodViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        apodViewModel =
            ViewModelProviders.of(this).get(ApodViewModel::class.java)
        val root = inflater.inflate(R.layout.list_fragment_apod, null)
        val pullToRefresh = root.findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        pullToRefresh.setOnRefreshListener{
            pullToRefresh.setRefreshing(false)
        }

        controller = ApodController(activity!!)
      //  controller.let { Log.d("Controller", "controller.let") }

            controller.getStartData()



//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root
    }

    fun getStartData(){


    }


}