package com.nasa.nasastarsnews.ui.apod


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nasa.nasastarsnews.data.ApodData

class ApodViewModel : ViewModel() {

    val listApodData = mutableListOf<Any?>()
    var position:Int = 0
 //   var countNotDataGlobal = 0

    val countNotDataGlobal = MutableLiveData<Int>().apply {
        value = 0
    }

//    val text: LiveData<String> = _text

    fun getApod():ApodData{
        return listApodData[position] as ApodData
    }
}