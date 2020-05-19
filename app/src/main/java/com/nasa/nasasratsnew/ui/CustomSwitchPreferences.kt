package com.nasa.nasasratsnew.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.preference.PreferenceViewHolder
import androidx.preference.SwitchPreference
import com.nasa.nasasratsnew.R

class CustomSwitchPreferences: SwitchPreference{

    lateinit var textViewTitle:TextView

    constructor(context: Context): super(context){

    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs){

    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int): super(context, attrs, defStyleAttr){

    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int, defStyleRes:Int): super(context, attrs, defStyleAttr, defStyleRes){

    }


    override fun isSelectable(): Boolean {
        return super.isSelectable()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        textViewTitle = holder!!.itemView!!.findViewById<TextView>(android.R.id.title)!!
        if(isSelectable){
            textViewTitle.setTextColor(R.color.colorAccent)
        }
        notifyChanged()


    }

}