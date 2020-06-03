package com.sn.starsnews.controller

import android.content.Context
import android.util.Log
import com.sn.starsnews.MainActivity
import com.sn.starsnews.data.ApodData
import org.json.JSONObject

class CreatorApodObject (val id:Int,private val keyBatch_:Int, private val context: Context,
                         nasaUrl:String?,
                         var callbackToController: (apod:ApodData?, error: String?, keyBatch:Int) -> Unit){


    private val callbackToCreator = {response:String, status:Boolean -> responseFromNasa(response, status)}
    private val callbackToCreatorText = {response:String, status:Boolean -> responseTextTranslate(response, status)}
    private val callbackToCreatorTitle = {response:String, status:Boolean -> responseTitleTranslate(response, status)}
    private var apod:ApodData? = null
    private val typeMediaVideo = "video"

    init{
        RequestByUrl(context, nasaUrl, callbackToCreator )
    }

    private fun responseFromNasa(response:String, status:Boolean){
        if(status){
            parserNasaJson(response)
            if(MainActivity.language != MainActivity.languageDefault){
                Log.d("MyCont", " translate(apod)  ")
                translate(apod)

            } else {
                callbackToController(apod, null, keyBatch_)
            }
        } else {
            errorLoad(response)
        }


    }

    private fun parserNasaJson(response: String){
        val json= JSONObject(response)
        val date:String = json.getString("date")
        val text:String = json.getString("explanation")
        val typeMedia:String = json.getString("media_type")
        val url:String = json.getString("url")

        val title:String = json.getString("title")

        val hdUrl:String? = json.optString("hdurl")

        apod = ApodData(id, date, text, typeMedia, title)
        apod?.url = url
        apod?.hdUrl = hdUrl

        if(typeMedia == typeMediaVideo && url.contains("https://www.youtube.com/embed/")){

            val tempId = url.substring(30)

            var id = ""
            run loop@{
                tempId.forEach {
                    if(it != '/' && it != '?'){
                        id +=it
                        Log.d("MyCont", "id  = $id ")

                    } else {
                        return@loop
                    }
                }
            }
            apod?.youtubeId = id
        }
        Log.d("MyCont", "id = $id , date= $date , typemedia= $typeMedia")

    }

    private fun translate(apodEnglish:ApodData?){

        val titleTemp = apodEnglish?.title!!.replace(" ", "%20")
        val translateUrlTitle = creatorTranslateUrl(titleTemp)

        val textTemp = apodEnglish.text.replace(" ", "%20")
        val translateUrlText = creatorTranslateUrl(textTemp)

        RequestByUrl(context, translateUrlText, callbackToCreatorText)
        RequestByUrl(context, translateUrlTitle, callbackToCreatorTitle)

    }

    private fun creatorTranslateUrl(text:String?): String{
        val yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20200501T062736Z.e9edef3c7dc2f9b4.f15c6e7469b76e0aa102f6f4fa840a76692ff4dd&format=plain&options=1"
        val textUrl = "&text="
        val languageUrl = "&lang=en-"

        return yandexUrl + languageUrl + MainActivity.language + textUrl + text
    }

    private fun parserTranslateJson(response: String) :String{
        val json= JSONObject(response)
        val textTranslate:String = json.getString("text")
        val textTranslateNew = textTranslate.substring(2, textTranslate.length-2)

        return textTranslateNew

    }
    private fun responseTextTranslate(textTranslate: String, status:Boolean){
        if(status){
            val text = parserTranslateJson(textTranslate)
            sendTranslatedApod(text, 2)
        } else {
            errorLoad(textTranslate)
        }


    }
    private fun responseTitleTranslate(titleTranslate: String, status:Boolean){
        if(status){
            val text = parserTranslateJson(titleTranslate)
            sendTranslatedApod(text, 1)
        } else {
            errorLoad(titleTranslate)
        }


    }

    private fun sendTranslatedApod(text: String, key:Int){
        if(key == 1){
            apod?.titleTranslate = text
        } else {
            apod?.textTranslate = text
        }

        if(apod?.titleTranslate != null && apod?.textTranslate != null){
            Log.d("MyCont", "id = $id , date= ${apod!!.date} , text translate = ${apod!!.textTranslate}e")
            callbackToController.invoke(apod, null, keyBatch_)
        }


    }

    private fun errorLoad(error:String){
        callbackToController.invoke(null, error, keyBatch_)
    //    Log.d("MyCont", "private fun errorLoad $error")

    }



}