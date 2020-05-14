package com.nasa.nasasratsnew.ui

import android.content.SharedPreferences
import android.os.Bundle
import 	androidx.preference.PreferenceFragmentCompat
import com.nasa.nasasratsnew.MainActivity
import com.nasa.nasasratsnew.R

class CustomPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {




    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }
}