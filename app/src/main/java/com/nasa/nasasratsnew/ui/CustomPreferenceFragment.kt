package com.nasa.nasasratsnew.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.*
import androidx.recyclerview.widget.RecyclerView
import com.nasa.nasasratsnew.MainActivity
import com.nasa.nasasratsnew.R
import kotlinx.android.synthetic.main.nav_header_main.*

class CustomPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var defaultView: View


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)



    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        if(key == MainActivity.key_use_hd){
            MainActivity.isHDImage = sharedPreferences?.getBoolean(key, MainActivity.isHDImageDefault)!!

        } else if (key == MainActivity.key_language){
            MainActivity.language = sharedPreferences?.getString(key, MainActivity.languageDefault)!!
            manageSwitchTwoText()
        } else if (key == MainActivity.key_two_text){
            MainActivity.twoText = sharedPreferences?.getBoolean(key, MainActivity.twoTextDefault)!!

        }
    }


    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        manageSwitchTwoText()
    }


    private fun manageSwitchTwoText(){
        val languageValue = preferenceManager.sharedPreferences.getString(MainActivity.key_language, MainActivity.languageDefault)
        val switchPreference: CustomSwitchPreferences? = preferenceScreen.get<CustomSwitchPreferences>(MainActivity.key_two_text)

        if (languageValue == MainActivity.languageDefault){
            switchPreference?.isSelectable = false
            switchPreference?.isChecked = false
            Log.d("MyCont", "isSelectable = false")
        } else {
            switchPreference?.isSelectable = true
            Log.d("MyCont", "isSelectable = true")

        }


    }


}