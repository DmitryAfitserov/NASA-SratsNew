package com.nasa.nasastarsnews.controller




import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData

import com.nasa.nasastarsnews.data.ApodData
import com.nasa.nasastarsnews.data.UrlData
import com.nasa.nasastarsnews.ui.apod.ApodListFragment
import java.text.SimpleDateFormat
import java.util.*

class ApodControllerText(private val context: Context, private val apodListFragment: ApodListFragment, private var listApodData:MutableList<Any?>, private var countNotDataGlobal:MutableLiveData<Int>) {

    private var URL = "https://api.nasa.gov/planetary/apod?api_key=mtLZUxtBo45hYfKLteWj3rH8qBv0b93cZz7aXqDe"
    private var URL_date = "&date="

    private var dateFormat:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var dateNow:Date = Date()

    private var listURL = arrayListOf<UrlData?>()

    val startCountObjects:Int = 5 // 6 is really

    private val usuallyCountObjects:Int = 3 // 4 is really


    private val callbackToController =
        {apod:Any, error:String?, keyBatch:Int -> responseFromCreator(apod, error, keyBatch) }

    private var listTemp = mutableListOf<ApodData>()

    private var keyBatch:Int = 1

    private var countNotDataLocal = 0


    fun work(firstVisibleItem:Int): Boolean{
        Log.d("MyCont", "countNotDataGlobal == ${countNotDataGlobal.value}")
        if(firstVisibleItem == -1 && keyBatch >= 0){
            Log.d("MyCont", "firstVisibleItem == -1")
            keyBatch = -1
            countNotDataLocal = 0
            countNotDataGlobal.value = 0
         //   isSaveList = true
            listURL.clear()
            creatorURL(startCountObjects)

            for(i in 0..startCountObjects){
                CreatorApodObject(i,keyBatch,  context, listURL[i]?.url, callbackToController)
            }
        }


        if (listApodData.isEmpty() && listURL.isEmpty() && keyBatch >= 0){
             Log.d("MyCont", "startCountObjects = 5")
            keyBatch = 1
            countNotDataLocal = 0
             getData(startCountObjects)

         }
        if(firstVisibleItem +11 > listApodData.size && listApodData.isNotEmpty() && keyBatch >= 0){
            if(listApodData.size < listURL.size){
                Log.d("MyCont", " return false")
                return false
            }

            Log.d("MyCont", "usuallycountObjects = 3")
            if(keyBatch < 2){
                keyBatch = 2
            } else {
                keyBatch++
            }
            countNotDataLocal = 0

            getData(usuallyCountObjects)
        }
        Log.d("MyCont", "work")
        return true

    }

    private fun getData(countLoadObjects:Int){
        creatorURL(countLoadObjects)
        var listSize = 0
        if(listApodData.isNotEmpty()){
            listSize = listApodData.size - 1 //- countNotDataGlobal
        }


        for(i in listSize..listSize+countLoadObjects){
            CreatorApodObject(i,keyBatch,  context, listURL[i]?.url, callbackToController)
        }

    }



    private fun creatorURL(countCreateURL:Int){

        var listSize = 0
        if(listApodData.isNotEmpty() && keyBatch >= 0){
            listSize = listApodData.size - 1
        }


        while (listURL.size < listSize){
            listURL.add(null)
            Log.d("MyCont", " listURL.add(null)")
        }


        val offsetDay = listURL.size + countNotDataGlobal.value!!


        val cal:Calendar = Calendar.getInstance()
        cal.time = dateNow

        var workDate:Date

        for (count in 0..countCreateURL){
            cal.add(Calendar.DATE, -(offsetDay + count))
            workDate = cal.time
            val url = UrlData(URL + URL_date + dateFormat.format(workDate), listURL.size)
            listURL.add(url)

          //  listURL.add(URL + URL_date + dateFormat.format(workDate))
//            Log.d("MyCont", listURL[count]!!)
            cal.time = dateNow
        }

    }


    private fun responseFromCreator(apod:Any, error:String?, key:Int){

        if(key == keyBatch){   // check batch

            error?.let {
                Log.d("MyCont", "error = ${error} ")
                if(error == "com.android.volley.ServerError" || error == "com.android.volley.ClientError"){
                    countNotDataLocal++
                    // lits not work
                    Log.d("MyCont", "error id = ${(apod as Int)} ")


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        listURL.removeIf { it!!.id == (apod as Int) }
                    } else {
                        
                    }

                    for(i in listURL){
                        Log.d("MyCont", "listUrl after remove id = ${i?.id} ")
                    }

                    Log.d("MyCont", "error = ${error} (apod as Int) == 0")
                } else {
                    responseErrorLoad(it)
                    return
                }

            } ?: run {

                listTemp.add(apod as ApodData)

            }



            if(keyBatch <= 1){
                if(listTemp.size ==startCountObjects + 1 - countNotDataLocal){
                    Log.d("MyCont", "keyBatch <= 1")
                    countNotDataGlobal.value = countNotDataLocal + countNotDataGlobal.value!!
                    responseSuccessLoad()

                }
            } else {
                if(listTemp.size ==usuallyCountObjects + 1 - countNotDataLocal ){
                    countNotDataGlobal.value =countNotDataLocal + countNotDataGlobal.value!!
                    responseSuccessLoad()
                }
            }

        }

    }


    private fun responseSuccessLoad(){

        Log.d("MyCont", "responseSuccessLoad")
        listTemp.sortBy { it.id }
        Log.d("MyCont", "sortBy listTemp.size = ${listTemp.size}")
        if(keyBatch < 0){
            Log.d("MyCont", "isSaveList == true")
            listApodData.clear()
            listApodData.addAll(listTemp)
            keyBatch = 1
        } else {
            if(listApodData.isNotEmpty()){
                listApodData.removeAt(listApodData.size -1)
            }
            for(apodObject in listTemp){
                listApodData.add(apodObject)

            }
        }

        listApodData.add(false)
        apodListFragment.dataAvailable()
        listTemp.clear()

        if(keyBatch == 1){
            keyBatch++
            work(4)

        }

    }

    private fun responseErrorLoad(error:String){


            if(keyBatch <= 1){
                keyBatch = 0
                listURL.clear()
            } else {
                keyBatch =+ 1
                if(listApodData[listApodData.size -1] != true){
                    listApodData[listApodData.size -1] = true
                }
                for(i in 0..usuallyCountObjects - countNotDataLocal){
                    listURL.removeAt(listURL.size - 1)
                }
            }



        listTemp.clear()
        Log.d("MyCont", "responseErrorLoad")

        apodListFragment.errorLoadData(error)
    }



}