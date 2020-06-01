package com.nasa.nasastarsnews.data


data class ApodData(val id:Int, val date:String, val text: String , val typeMedia: String, val title:String){


    var hdUrl:String? = null
    var url:String? = null




    var textTranslate:String? = null
    var titleTranslate:String? = null

    var width:Int? = null
    var height:Int? = null







}
