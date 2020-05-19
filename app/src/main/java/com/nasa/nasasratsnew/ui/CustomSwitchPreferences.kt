package com.nasa.nasasratsnew.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.preference.PreferenceViewHolder
import androidx.preference.SwitchPreference
import com.nasa.nasasratsnew.R

class CustomSwitchPreferences: SwitchPreference{


    constructor( context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int, defStyleRes:Int): super(context, attrs, defStyleAttr, defStyleRes)


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)

        Log.d("MyCont", "onBindViewHolder")
        val textViewTitle = holder!!.itemView.findViewById<TextView>(android.R.id.title)!!
        if(isSelectable){
            Log.d("MyCont", "setTextColor(R.color.colorAccent)")

            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                textViewTitle.setTextColor(context.getColor(R.color.colorBlackText))
            } else {
                textViewTitle.setTextColor(context.resources.getColor(R.color.colorBlackText))
            }

        }

    }

}