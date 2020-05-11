package com.nasa.nasasratsnew.ui.apod

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.nasa.nasasratsnew.R
import com.nasa.nasasratsnew.data.ApodData

class AdapterListApod(activity: FragmentActivity, list: MutableList<Any?>) :

    ArrayAdapter<Any>(activity, R.layout.custom_item_apod, list) {

    var vi: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        val retView: View

      //  if(getItem(position) is ApodData){

            if(convertView == null){
                retView = vi.inflate(R.layout.custom_item_apod, null)
                holder = ViewHolder()

                holder.text = retView.findViewById(R.id.text) as TextView?

                holder.text?.text = (getItem(position) as ApodData).id.toString()

                retView.tag = holder

            } else {
                holder = convertView.tag as ViewHolder
                
                retView = convertView
            }
//        } else {
//
//            if(convertView == null){
//                retView = vi.inflate(R.layout.custom_item_apod_error, null)
//                holder = ViewHolder()
//
//                holder.text = retView.findViewById(R.id.text) as TextView?
//
//
//                retView.tag = holder
//
//            } else {
//                holder = convertView.tag as ViewHolder
//                retView = convertView
//            }
//
//        }

        return retView
    }



    internal class ViewHolder {
        var text: TextView? = null
    }


}