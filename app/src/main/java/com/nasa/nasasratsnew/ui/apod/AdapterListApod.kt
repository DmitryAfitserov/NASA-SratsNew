package com.nasa.nasasratsnew.ui.apod

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.nasa.nasasratsnew.MainActivity
import com.nasa.nasasratsnew.R
import com.nasa.nasasratsnew.data.ApodData
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class AdapterListApod(activity: FragmentActivity, list: MutableList<Any?>) :

    ArrayAdapter<Any>(activity, R.layout.custom_item_apod, list) {

    var vi: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val id:Int = 155
    private val typeMediaImage = "image"
    private var positionGlobal = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        val retView: View

        if(getItem(position) is ApodData){
            positionGlobal = position

            if(convertView == null || convertView.id == id){
                retView = vi.inflate(R.layout.custom_item_apod, null)
                holder = ViewHolder()

                holder.text = retView.findViewById(R.id.text_text_view) as TextView?
                holder.title = retView.findViewById(R.id.title_text_view) as TextView?
                holder.date = retView.findViewById(R.id.date_text_view) as TextView?

                fillTextView(holder, position)

                fillImageView(holder, position, retView)

                retView.tag = holder

            } else {
                holder = convertView.tag as ViewHolder

                fillTextView(holder, position)

                fillImageView(holder, position, convertView)

                convertView.clearFocus()
                return convertView
            }
        } else {

            if(getItem(position) == true){
                retView = vi.inflate(R.layout.custom_item_apod_error, null)
                retView.id = id
                holder = ViewHolder()

                holder.text = retView.findViewById(R.id.text) as TextView?

                retView.tag = holder
            } else {
                    retView = vi.inflate(R.layout.custom_item_apod_loading, null)
                    retView.id = id
                    holder = ViewHolder()

                    retView.tag = holder
            }
        }
        retView.clearFocus()
        return retView
    }

    private fun fillTextView(holder:ViewHolder, position:Int){
        if(MainActivity.language == MainActivity.languageDefault){
            holder.text?.text = (getItem(position) as ApodData).text
            holder.title?.text = (getItem(position) as ApodData).title

        } else {
            (getItem(position) as ApodData).textTranslate?.let {
                holder.text?.text = (getItem(position) as ApodData).textTranslate
            } ?: run {
                holder.text?.text = (getItem(position) as ApodData).text
            }

            (getItem(position) as ApodData).titleTranslate?.let {
                holder.title?.text = (getItem(position) as ApodData).titleTranslate
            } ?: run {
                holder.title?.text = (getItem(position) as ApodData).title
            }
        }
        when(holder.title?.lineCount){
            1 -> holder.text?.maxLines = 9
            2 -> holder.text?.maxLines = 8
            3 -> holder.text?.maxLines = 7

        }

        holder.date?.text = (getItem(position) as ApodData).date
    }

    private fun fillImageView(holder:ViewHolder, position: Int, customView:View){
        val isImageLoad = typeMediaImage == (getItem(position) as ApodData).typeMedia
        if(isImageLoad){
            holder.image?.let {

                picassoLoad((getItem(position) as ApodData).url, holder, position)

            } ?: run {
                holder.image = customView.findViewById(R.id.imageView) as ImageView?
                picassoLoad((getItem(position) as ApodData).hdUrl, holder, position)
            }

        } else {
            holder.image?.let {

                picassoLoad(holder)

            } ?: run {
                holder.image = customView.findViewById(R.id.imageView) as ImageView?
                picassoLoad(holder)
            }
        }
    }

    private fun picassoLoad(holder:ViewHolder){
        Picasso.get()
            .load(R.drawable.nasa)
            //  .placeholder(R.drawable.user_placeholder)
            //  .error(R.drawable.user_placeholder_error)
            .into(holder.image, object : Callback{
                override fun onSuccess() {
                    val h = holder.image?.height
                    val w = holder.image?.width
                    Log.d("MyCont", "h = $h , w = $w , tag =  ${holder.image?.tag}")
                }

                override fun onError(e: Exception?) {

                }
            })
    }


    private fun picassoLoad(url:String?, holder:ViewHolder, position: Int){
        Picasso.get()
            .load(url)
            //  .placeholder(R.drawable.user_placeholder)
            //  .error(R.drawable.user_placeholder_error)
            .into(holder.image, object : Callback{
                override fun onSuccess() {
                    val h = holder.image?.height
                    val w = holder.image?.width
                    Log.d("MyCont", "h = $h , w = $w , tag =  $position")
                }

                override fun onError(e: Exception?) {

                }
            })
    }


    internal class ViewHolder {
        var text: TextView? = null
        var title: TextView? = null
        var date: TextView? = null
        var image: ImageView? = null
    }


}