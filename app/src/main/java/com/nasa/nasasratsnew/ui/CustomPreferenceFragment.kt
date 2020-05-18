package com.nasa.nasasratsnew.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import 	androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.preference.get
import com.nasa.nasasratsnew.MainActivity
import com.nasa.nasasratsnew.R

class CustomPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {




    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)

        manageSwitchTwoText()

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
    }

    private fun manageSwitchTwoText(){
        val languageValue = preferenceManager.sharedPreferences.getString(MainActivity.key_language, MainActivity.languageDefault)
        val switchPreference = preferenceScreen.get<SwitchPreference>(MainActivity.key_two_text)
        if (languageValue == MainActivity.languageDefault){
            switchPreference?.isSelectable = false
            switchPreference?.isChecked = false
        } else {
            switchPreference?.isSelectable = true
            switchPreference?.disableDependentsState = true


        }
    }

}