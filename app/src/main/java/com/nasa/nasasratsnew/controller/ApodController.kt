package com.nasa.nasasratsnew.controller



import android.app.DownloadManager
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nasa.nasasratsnew.ui.apod.ApodViewModel

class ApodController(val activity: FragmentActivity) {

    private var URL = "https://api.nasa.gov/planetary/apod?api_key=mtLZUxtBo45hYfKLteWj3rH8qBv0b93cZz7aXqDe"

    private lateinit var apodViewModel: ApodViewModel

    fun getStartData(){
        Log.d("Controller", "getStartData")

        apodViewModel = ViewModelProviders.of(activity).get(ApodViewModel::class.java)
        val queue = Volley.newRequestQueue(activity)



        val stringRequest = StringRequest(
            Request.Method.GET, URL,
            Response.Listener<String> { response ->
                Log.d("Controller", response.toString())
            },
            Response.ErrorListener { error ->  Log.d("Controller", "error load url"+ error) })

        queue.add(stringRequest)
    }

}