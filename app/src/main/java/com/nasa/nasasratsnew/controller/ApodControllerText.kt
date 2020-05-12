package com.nasa.nasasratsnew.controller




import android.content.Context
import android.util.Log

import com.nasa.nasasratsnew.data.ApodData
import com.nasa.nasasratsnew.ui.apod.ApodListFragment
import java.text.SimpleDateFormat
import java.util.*

class ApodControllerText(private val context: Context, private val apodListFragment: ApodListFragment, private var listApodData:MutableList<Any?>) {

    private var URL = "https://api.nasa.gov/planetary/apod?api_key=mtLZUxtBo45hYfKLteWj3rH8qBv0b93cZz7aXqDe"
    private var URL_date = "&date="

    private var dateFormat:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var dateNow:Date = Date()

    private var listURL = arrayListOf<String?>()

    val startCountObjects:Int = 5 // 6 is really

    private val usuallycountObjects:Int = 3 // 4 is really


    private val callbackToController =
        {apod:ApodData?, error:String?, keyBatch:Int -> responseFromCreator(apod, error, keyBatch) }

    private var listTemp = mutableListOf<ApodData>()

    private var keyBatch:Int = 1



    fun work(firstVisibleItem:Int): Boolean{


        if (listApodData.isEmpty() && listURL.isEmpty()){
             Log.d("MyCont", "startCountObjects = 5")
            keyBatch = 1
             getData(startCountObjects)

         }
        if(firstVisibleItem +11 > listApodData.size && listApodData.isNotEmpty()){
            if(listApodData.size < listURL.size){
                Log.d("MyCont", " return false")
                return false
            }
//            if(listApodData[listApodData.size -1] is Boolean){
//
//                Log.d("MyCont", "delete null")
//            }
            Log.d("MyCont", "usuallycountObjects = 3")
            keyBatch++
            getData(usuallycountObjects)
        }
        Log.d("MyCont", "work")
        return true

    }

    private fun getData(countLoadObjects:Int){
        creatorURL(countLoadObjects)
        var listSize = 0
        if(listApodData.isNotEmpty()){
            listSize = listApodData.size -1
        }


        for(i in listSize..listSize+countLoadObjects){
            CreatorApodObject(i,keyBatch,  context, listURL[i], null, callbackToController)
        }

    }



    private fun creatorURL(countCreateURL:Int){

        var listSize = 0
        if(listApodData.isNotEmpty()){
            listSize = listApodData.size - 1
        }

        while (listURL.size < listSize){
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

    private fun responseFromCreator(apod:ApodData?, error:String?, key:Int){

        if(key == keyBatch){   // check batch

            error?.let {
                responseErrorLoad(it)
                return
            }

            listTemp.add(apod!!)

            if(keyBatch == 1){
                if(listTemp.size ==startCountObjects + 1 ){
                    responseSuccessLoad()
                }
            } else {
                if(listTemp.size ==usuallycountObjects + 1 ){
                    responseSuccessLoad()
                }
            }

        }

    }

    private fun responseSuccessLoad(){
        Log.d("MyCont", "responseSuccessLoad")
        listTemp.sortBy { it.id }
        if(listApodData.isNotEmpty()){
            listApodData.removeAt(listApodData.size -1)
        }
        for(apodObject in listTemp){
            listApodData.add(apodObject)

        }
        listApodData.add(false)
        apodListFragment.dataAvailable()
        listTemp.clear()

        if(keyBatch == 1){
            keyBatch++
            getData(usuallycountObjects)

        }

    }

    private fun responseErrorLoad(error:String){

        if(keyBatch == 1){
            keyBatch = 0
            listURL.clear()
        } else {
            keyBatch =+ 1
            if(listApodData[listApodData.size -1] != true){
                listApodData[listApodData.size -1] = true
            }
            for(i in 0..usuallycountObjects){
                listURL.removeAt(listURL.size - 1)
            }
        }

        listTemp.clear()
        Log.d("MyCont", "responseErrorLoad")

        apodListFragment.errorLoadData(error)
    }



}