package com.nasa.nasasratsnew.ui.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nasa.nasasratsnew.data.ApodData

class ApodViewModel : ViewModel() {

    val listApodData = mutableListOf<Any?>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text
}