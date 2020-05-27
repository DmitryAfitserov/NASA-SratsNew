package com.nasa.nasastarsnews.ui.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nasa.nasastarsnews.data.ApodData

class ApodViewModel : ViewModel() {

    val listApodData = mutableListOf<Any?>()
    public var position:Int = 0

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    public fun getApod():ApodData{
        return listApodData[position] as ApodData
    }
}