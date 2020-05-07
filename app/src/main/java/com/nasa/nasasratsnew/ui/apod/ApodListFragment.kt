package com.nasa.nasasratsnew.ui.apod

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
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


class ApodListFragment : ListFragment(), InterfaceForListApod, AbsListView.OnScrollListener {


    private lateinit var controller:ApodController
    private var apodViewModel: ApodViewModel? = null
    private var listApod:MutableList<ApodData?>? = null
    private var listAdapterApod:AdapterListApod? = null



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

        listView.setOnScrollListener(this)

//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        listAdapterApod = AdapterListApod(activity!!, listApod!!)
        listAdapter = listAdapterApod

        super.onActivityCreated(savedInstanceState)
    }

    private fun showStartContent(){
        (activity as AppCompatActivity).toolbar.visibility = View.VISIBLE
        (activity as AppCompatActivity).drawer_layout.visibility = View.VISIBLE
        (activity as AppCompatActivity).nav_view.visibility = View.VISIBLE
        listAdapterApod!!.notifyDataSetChanged()


    }



    override fun statrDataAvailable() {
        Log.d("MyCont", "showStartContent()")
        showStartContent()
    }


    override fun onScroll(
        view: AbsListView?,
        firstVisibleItem: Int,
        visibleItemCount: Int,
        totalItemCount: Int
    ) {
        Log.d("MyCont", "firstVisibleItem $firstVisibleItem , visibleItemCount = $visibleItemCount , totalItemCount = $totalItemCount")
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
        Log.d("MyCont", "scrollState = $scrollState")
    }
}


