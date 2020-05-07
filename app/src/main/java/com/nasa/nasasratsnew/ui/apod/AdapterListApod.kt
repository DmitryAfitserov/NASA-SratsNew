package com.nasa.nasasratsnew.ui.apod

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.nasa.nasasratsnew.R
import com.nasa.nasasratsnew.data.ApodData
import org.w3c.dom.Text

class AdapterListApod(private val activity: FragmentActivity, private val list: MutableList<ApodData?>) :

    ArrayAdapter<ApodData>(activity, R.layout.custom_item_apod, list) {

    var vi: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var holder: ViewHolder
        var retView: View


        if(convertView == null){
            retView = vi.inflate(R.layout.custom_item_apod, null)
            holder = ViewHolder()

            holder.text = retView.findViewById(R.id.text) as TextView?

            holder.text?.text = getItem(position)?.textTranslate

            retView.tag = holder

        } else {
            holder = convertView.tag as ViewHolder
            retView = convertView
        }

        return retView
    }

    internal class ViewHolder {
        var text: TextView? = null
    }


}