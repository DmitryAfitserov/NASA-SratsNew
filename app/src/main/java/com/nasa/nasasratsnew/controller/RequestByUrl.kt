package com.nasa.nasasratsnew.controller

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RequestByUrl(private val context: Context,private val id:Int, private val url:String?, val lambda:Unit){

    init {

    }

    private fun createQuery(){

        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Controller", response.toString())
            },
            Response.ErrorListener { error ->  Log.d("Controller", "error load url"+ error) })

        queue.add(stringRequest)
    }

}