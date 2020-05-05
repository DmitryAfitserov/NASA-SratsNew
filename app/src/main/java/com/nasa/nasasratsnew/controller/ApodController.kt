package com.nasa.nasasratsnew.controller



import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nasa.nasasratsnew.data.ApodData
import com.nasa.nasasratsnew.ui.apod.ApodListFragment

class ApodController(private val activity: FragmentActivity, private val apodListFragment: ApodListFragment, private var listApodData:ArrayList<ApodData>) {

    private var URL = "https://api.nasa.gov/planetary/apod?api_key=mtLZUxtBo45hYfKLteWj3rH8qBv0b93cZz7aXqDe"



    fun work(){

         if (listApodData.size < 4){
             getStartData()
         }

        val queue = Volley.newRequestQueue(activity)



        val stringRequest = StringRequest(
            Request.Method.GET, URL,
            Response.Listener<String> { response ->
                Log.d("Controller", response.toString())
            },
            Response.ErrorListener { error ->  Log.d("Controller", "error load url"+ error) })

        queue.add(stringRequest)
    }

    private fun getStartData(){
        





        apodListFragment.statrDataAvailable()
    }

}