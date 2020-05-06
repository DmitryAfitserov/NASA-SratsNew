package com.nasa.nasasratsnew.controller



import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nasa.nasasratsnew.data.ApodData
import com.nasa.nasasratsnew.ui.apod.ApodListFragment
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ApodController(private val context: Context, private val apodListFragment: ApodListFragment, private var listApodData:ArrayList<ApodData>) {

    private var URL = "https://api.nasa.gov/planetary/apod?api_key=mtLZUxtBo45hYfKLteWj3rH8qBv0b93cZz7aXqDe"
    private var URL_date = "&date="

    private var dateFormat:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var dateNow:Date = Date()
    private var listURL = arrayListOf<String?>()
    private val startCountObjects:Int = 5
    private val callback = {id:Int, response:String -> response(id, response) }



    fun work(){

         if (listApodData.size < startCountObjects){
             getStartData()
         }


    }

    private fun getStartData(){
        creatorURL(startCountObjects)
        for(i in 0..startCountObjects){
            RequestByUrl(context, i, listURL[i], callback )
        }

        apodListFragment.statrDataAvailable()
    }




    private fun creatorURL(countCreateURL:Int){

        while (listURL.size < listApodData.size){
            listURL.add(null)
        }

        val ofsetDay = listURL.size
        val cal:Calendar = Calendar.getInstance()
        cal.time = dateNow

        var workDate:Date

        for (count in 0..countCreateURL){
            cal.add(Calendar.DATE, -(ofsetDay + count))
            workDate = cal.time
            listURL.add(URL + URL_date + dateFormat.format(workDate))
          //  Log.d("MyCont", listURL.get(count))
            cal.time = dateNow
        }

    }

    private fun response(id:Int, response:String){
        parserNasaJson(id, response)
      //  Log.d("MyCont", " $id response $response")
    }

    private fun parserNasaJson(id:Int, response: String) :ApodData{
        val json= JSONObject(response)
        val date:String = json.getString("date")
        val text:String = json.getString("explanation")
        val typeMedia:String = json.getString("media_type")
        val title:String = json.getString("title")
        val url:String = json.getString("url")
        val hdUrl:String? = json.optString("hdurl")

        var apod = ApodData(id, date, text, typeMedia, title)
        apod.url = url
        apod.hdUrl = hdUrl
        Log.d("MyCont", "id = $id , date= $date , typemedia= $typeMedia")

        return apod






    }


}