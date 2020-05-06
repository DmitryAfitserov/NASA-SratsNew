package com.nasa.nasasratsnew.controller

import android.content.Context
import android.util.Log
import com.nasa.nasasratsnew.data.ApodData
import org.json.JSONObject

class CreatorApodObject (val id:Int, private val context: Context,
                         private val nasaUrl:String?, private val language:String?,
                         var callbackToController: (id:Int, apod:ApodData) -> Unit){

    private val callbackToCreator = {response:String -> responseFromNasa(response)}
    private val callbackToCreatorText = {response:String -> responseTextTranslate(response)}
    private val callbackToCreatorTitle = {response:String -> responseTitleTranslate(response)}

    init{
        RequestByUrl(context, nasaUrl, callbackToCreator )
    }

    private fun responseFromNasa(response:String){
        val apodEnglish =  parserNasaJson(response)
        language?.let { translate(apodEnglish) } ?: run { callbackToController(id, apodEnglish) }

    }

    private fun parserNasaJson(response: String) :ApodData{
        val json= JSONObject(response)
        val date:String = json.getString("date")
        val text:String = json.getString("explanation")
        val typeMedia:String = json.getString("media_type")
        val title:String = json.getString("title")
        val url:String = json.getString("url")
        val hdUrl:String? = json.optString("hdurl")

        val apod = ApodData(id, date, text, typeMedia, title)
        apod.url = url
        apod.hdUrl = hdUrl
        Log.d("MyCont", "id = $id , date= $date , typemedia= $typeMedia")

        return apod
    }

    private fun translate(apodEnglish:ApodData){

        val translateUrlTitle = creatorTranslateUrl(apodEnglish.title)
        val translateUrlText = creatorTranslateUrl(apodEnglish.text)
        Log.d("MyCont", translateUrlTitle)
        Log.d("MyCont", translateUrlText)
        RequestByUrl(context, translateUrlText, callbackToCreatorText)
        RequestByUrl(context, translateUrlTitle, callbackToCreatorTitle)





    }

    private fun creatorTranslateUrl(text:String): String{
        val yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20200501T062736Z.e9edef3c7dc2f9b4.f15c6e7469b76e0aa102f6f4fa840a76692ff4dd&format=plain&options=1"
        val textUrl = "&text="
        val languageUrl = "&lang=en-"

        return yandexUrl + languageUrl + language + textUrl + text
    }

    private fun parserTranslateJson(response: String) :String{
        val json= JSONObject(response)
        val textTranslate:String = json.getString("text")

        return textTranslate

    }
    private fun responseTextTranslate(textTranslate: String){
        val text = parserTranslateJson(textTranslate)

    }
    private fun responseTitleTranslate(titleTranslate: String){
        val text = parserTranslateJson(titleTranslate)

    }

    private fun sednTranslatedApod(text: String){
        

        callbackToController.invoke(id, apod)
    }



}