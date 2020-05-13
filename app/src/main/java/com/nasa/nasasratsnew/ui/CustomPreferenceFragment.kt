package com.nasa.nasasratsnew.ui

import android.os.Bundle
import 	androidx.preference.PreferenceFragmentCompat
import com.nasa.nasasratsnew.R

class CustomPreferenceFragment : PreferenceFragmentCompat(){


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }




}