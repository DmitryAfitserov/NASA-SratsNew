package com.nasa.nasasratsnew.ui.apod

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nasa.nasasratsnew.R
import com.nasa.nasasratsnew.controller.ApodControllerText
import com.nasa.nasasratsnew.interfaces.InterfaceForListApod
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class ApodListFragment : ListFragment(), InterfaceForListApod, AbsListView.OnScrollListener {

    private lateinit var controller:ApodControllerText
    private var apodViewModel: ApodViewModel? = null
    private lateinit var listApod:MutableList<Any?>
    private var listAdapterApod:AdapterListApod? = null
    private var pullToRefresh:SwipeRefreshLayout? = null
    private var sendedFirstItem = 0
    private var root:View? = null
    private lateinit var layoutError:LinearLayout
    private lateinit var textViewError:TextView
    private lateinit var buttonError:Button
    private lateinit var progressBarError:ProgressBar



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        apodViewModel =
            ViewModelProviders.of(activity!!).get(ApodViewModel::class.java)

        listApod = apodViewModel!!.listApodData

        root = inflater.inflate(R.layout.list_fragment_apod, null)

        layoutError = root!!.findViewById(R.id.layout_error)
        textViewError = root!!.findViewById(R.id.text_view_error)
        buttonError = root!!.findViewById(R.id.button_error)
        progressBarError = root!!.findViewById(R.id.progress_bar_error)


        pullToRefresh = root?.findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        pullToRefresh?.setOnRefreshListener{
            sendedFirstItem = -1
          controller.work(sendedFirstItem)
        }

        controller = ApodControllerText(context!!, this, listApod)


            controller.work(0)

//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        listAdapterApod = AdapterListApod(activity!!, listApod)
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
        if(listApod.size < controller.startCountObjects +3 ){
            activity?.let {
                (activity as AppCompatActivity).toolbar.visibility = View.VISIBLE
                (activity as AppCompatActivity).app_bar_layout.visibility = View.VISIBLE
                (activity as AppCompatActivity).nasa_start_image.visibility = View.INVISIBLE
            }

        }

    }
    private fun hideViewElements(){
        if(listApod.size == 0){
            activity?.let {
                (activity as AppCompatActivity).toolbar.visibility = View.INVISIBLE
                (activity as AppCompatActivity).app_bar_layout.visibility = View.INVISIBLE
                (activity as AppCompatActivity).nasa_start_image.visibility = View.VISIBLE
            }
        }
    }

    private fun showViewErrorElements(){
        activity.let {
            layoutError.visibility = View.VISIBLE

            progressBarError.visibility = View.INVISIBLE

            textViewError.visibility = View.VISIBLE

            buttonError.visibility = View.VISIBLE
            buttonError.setOnClickListener {
                progressBarError.visibility = View.VISIBLE
                controller.work(0)
            }
        }


    }
    private fun hideViewErrorElements(){
        if(listApod.isNotEmpty() && listApod.size < controller.startCountObjects + 3) {
            activity?.let {
                layoutError.visibility = View.INVISIBLE
                textViewError.visibility = View.INVISIBLE
                buttonError.visibility = View.INVISIBLE
                progressBarError.visibility = View.INVISIBLE
            }
        }
    }

    private fun hideProgressBarScrollUpdate(){
        if(pullToRefresh?.isRefreshing == true){
            pullToRefresh?.isRefreshing = false
        }

    }


    override fun dataAvailable() {


        showViewElements()
        hideViewErrorElements()
        hideProgressBarScrollUpdate()
        listAdapterApod!!.notifyDataSetChanged()
        if(listApod.size < sendedFirstItem || sendedFirstItem < 0){
            sendedFirstItem =1
        }


    }

    override fun errorLoadData(error: String) {
        showViewElements()
        hideProgressBarScrollUpdate()
        if(sendedFirstItem < 0){
            sendedFirstItem = 1
            Toast.makeText(context, R.string.error_load_data, Toast.LENGTH_SHORT).show()
        }
        if(listApod.isEmpty()){
            showViewErrorElements()
        } else {
                listAdapterApod!!.notifyDataSetChanged()
        }

    }



    override fun onScroll(
        view: AbsListView?,
        firstVisibleItem: Int,
        visibleItemCount: Int,
        totalItemCount: Int
    ) {
        if(sendedFirstItem in 0 until firstVisibleItem){
            sendedFirstItem = firstVisibleItem
            controller.work(sendedFirstItem)
            Log.d("MyCont", "onScroll() firstVisibleItem = $firstVisibleItem ")
        }

    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
    }


    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        Log.d("MyCont", "position = $position ")
        if(position == listApod.size - 1){
            if(listApod[listApod.size - 1] == true){
                val progressBar = v.findViewById<ProgressBar>(R.id.progress_bar_error_item)
                progressBar?.visibility = View.VISIBLE
                v.findViewById<TextView>(R.id.text_view_error_item)?.visibility = View.INVISIBLE
                controller.work(sendedFirstItem)
            }
        } else {
            apodViewModel?.position = position
            Navigation.findNavController(view!!).navigate(R.id.to_fragment_apod)
        }
    }


}


