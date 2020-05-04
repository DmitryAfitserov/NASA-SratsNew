package com.nasa.nasasratsnew.ui.apod

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.nasa.nasasratsnew.R

class AdapterListApod(private val context: Activity, private val title: Array<String>,
                      private val description: Array<String>,
                      private val imgid: Array<Int>) :
    ArrayAdapter<String>(context, R.layout.custom_item_apod, title) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        return super.getView(position, convertView, parent)
    }


}