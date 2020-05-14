package com.nasa.nasasratsnew.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import 	androidx.preference.PreferenceFragmentCompat
import com.nasa.nasasratsnew.MainActivity
import com.nasa.nasasratsnew.R

class CustomPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {




    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.d("MyCont", "onSharedPreferenceChanged")
        if(key == MainActivity.key_use_hd){
            MainActivity.isHDImage = sharedPreferences?.getBoolean(key, false)!!

        } else if (key == MainActivity.key_language){
            MainActivity.language = sharedPreferences?.getString(key, "en")!!
        }
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
}