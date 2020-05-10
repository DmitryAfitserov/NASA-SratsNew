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
    private lateinit var listApod:MutableList<ApodData?>
    private var listAdapterApod:AdapterListApod? = null
    private var sendedFirstItem = 0



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



        controller = ApodController(context!!, this, listApod)

            controller.work(0)




//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        listAdapterApod = AdapterListApod(activity!!, listApod!!)
        listAdapter = listAdapterApod
        listView.setOnScrollListener(this)

        if(listApod.isNotEmpty()){
            showViewElements()
        } else {
            hideViewElements()
        }

        super.onActivityCreated(savedInstanceState)
    }

    private fun showViewElements(){
        if(listApod.size < controller.startCountObjects +2 ){

            (activity as AppCompatActivity).toolbar.visibility = View.VISIBLE
            (activity as AppCompatActivity).drawer_layout.visibility = View.VISIBLE
            (activity as AppCompatActivity).nav_view.visibility = View.VISIBLE

        }


    }
    private fun hideViewElements(){
        (activity as AppCompatActivity).toolbar.visibility = View.INVISIBLE
        (activity as AppCompatActivity).drawer_layout.visibility = View.INVISIBLE
        (activity as AppCompatActivity).nav_view.visibility = View.INVISIBLE

    }




    override fun dataAvailable() {
        Log.d("MyCont", "showContent()")
        listApod!!.add(null)

        showViewElements()
        listAdapterApod!!.notifyDataSetChanged()
    }

    override fun errorLoadData(error: String) {
        showViewElements()
        listAdapterApod!!.notifyDataSetChanged()

        Log.d("MyCont", "error showContent() = $error")
    }


    override fun onScroll(
        view: AbsListView?,
        firstVisibleItem: Int,
        visibleItemCount: Int,
        totalItemCount: Int
    ) {
        if(firstVisibleItem > sendedFirstItem){
            sendedFirstItem = firstVisibleItem
            controller.work(sendedFirstItem)
            Log.d("MyCont", "onScroll()")
        }

    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

    }


}


