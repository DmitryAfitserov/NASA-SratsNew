package com.nasa.nasasratsnew.ui

import android.content.SharedPreferences
import android.os.Bundle
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

    }
}