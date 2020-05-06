package com.nasa.nasasratsnew.ui.apod

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nasa.nasasratsnew.R
import com.nasa.nasasratsnew.controller.ApodController
import com.nasa.nasasratsnew.data.ApodData
import com.nasa.nasasratsnew.interfaces.InterfaceForListApod
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class ApodListFragment : ListFragment(), InterfaceForListApod {

    private lateinit var controller:ApodController
    private var apodViewModel: ApodViewModel? = null
    private var listApod:MutableList<ApodData?>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        apodViewModel =
            ViewModelProviders.of(this).get(ApodViewModel::class.java)

        listApod = apodViewModel!!.listApodData

        val root = inflater.inflate(R.layout.list_fragment_apod, null)
        val pullToRefresh = root.findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        pullToRefresh.setOnRefreshListener{
            pullToRefresh.setRefreshing(false)
        }

        controller = ApodController(context!!, this, listApod!!)

            controller.work()


//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root
    }

    private fun showStartContent(){
        (activity as AppCompatActivity).toolbar.visibility = View.VISIBLE
        (activity as AppCompatActivity).drawer_layout.visibility = View.VISIBLE
        (activity as AppCompatActivity).nav_view.visibility = View.VISIBLE


    }

    override fun statrDataAvailable() {
        Log.d("MyCont", "showStartContent()")
        showStartContent()
    }
}