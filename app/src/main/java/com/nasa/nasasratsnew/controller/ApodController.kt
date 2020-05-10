package com.nasa.nasasratsnew.controller




import android.content.Context
import android.util.Log

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

    private val usuallycountObjects:Int = 3 // 4 is really


    private val callbackToController = {apod:ApodData?, error:String? -> responseFromCreator(apod, error) }

    private var listTemp = mutableListOf<ApodData>()

    private var isStartData = true


    fun work(firstVisibleItem:Int): Boolean{

        if (listApodData.size < startCountObjects){
             Log.d("MyCont", "startCountObjects = 5")
             getData(startCountObjects)
         }
        if(firstVisibleItem +10 > listApodData.size && !isStartData){
            if(listApodData.size < listURL.size){
                return false
            }
            Log.d("MyCont", "usuallycountObjects = 3")
            getData(usuallycountObjects)
        }
        return true

    }

//    private fun getStartData(){
//        creatorURL(startCountObjects)
//        for(i in 0..startCountObjects){
//
//            CreatorApodObject(i, context, listURL[i], "ru", callbackToController)
//        }
//
//
//    }

    private fun getData(countLoadObjects:Int){
        creatorURL(countLoadObjects)
        for(i in listApodData.size..listApodData.size+countLoadObjects){
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

    private fun responseFromCreator(apod:ApodData?, error:String?){

        error?.let {
            responseErrorLoad(it)
            return
        }

        listTemp.add(apod!!)

        if(isStartData){
            if(listTemp.size ==startCountObjects + 1 ){
                responseSuccessLoad()
            }
        } else {
            if(listTemp.size ==usuallycountObjects + 1 ){
                responseSuccessLoad()
            }
        }


    }

    private fun responseSuccessLoad(){
        Log.d("MyCont", "responseSuccessLoad")
        listTemp.sortBy { it.id }
        for(apodObject in listTemp){
            listApodData.add(apodObject)

        }
        apodListFragment.statrDataAvailable()
        listTemp.clear()

        if(isStartData){
            getData(usuallycountObjects)
            isStartData =false
        }

    }

    private fun responseErrorLoad(error:String){

    }



}