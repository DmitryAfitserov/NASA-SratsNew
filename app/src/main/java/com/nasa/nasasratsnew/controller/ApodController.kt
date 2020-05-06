package com.nasa.nasasratsnew.controller




import android.content.Context

import com.nasa.nasasratsnew.data.ApodData
import com.nasa.nasasratsnew.ui.apod.ApodListFragment
import java.text.SimpleDateFormat
import java.util.*

class ApodController(private val context: Context, private val apodListFragment: ApodListFragment, private var listApodData:MutableList<ApodData?>) {

    private var URL = "https://api.nasa.gov/planetary/apod?api_key=mtLZUxtBo45hYfKLteWj3rH8qBv0b93cZz7aXqDe"
    private var URL_date = "&date="

    private var dateFormat:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var dateNow:Date = Date()

    private var listURL = arrayListOf<String?>()

    private val startCountObjects:Int = 5 // 6 is really

    private val callbackToController = {apod:ApodData? -> response(apod) }

    private var listTemp = mutableListOf<ApodData>()

    private var isStartData = true


    fun work(){

         if (listApodData.size < startCountObjects){

             getStartData()
         }


    }

    private fun getStartData(){
        creatorURL(startCountObjects)
        for(i in 0..startCountObjects){

            CreatorApodObject(i, context, listURL[i], "ru", callbackToController)
        }


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

    private fun response(apod:ApodData?){

        if(isStartData){
            listTemp.add(apod!!)

            if(listTemp.size == startCountObjects + 1 ){
                listTemp.sortBy { it.id }
                for(apodObject in listTemp){
                    listApodData.add(apodObject)

                }
                apodListFragment.statrDataAvailable()
                listTemp.clear()
                isStartData = false
            }
        }


    }



}