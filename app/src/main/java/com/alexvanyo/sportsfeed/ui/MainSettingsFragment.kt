package com.alexvanyo.sportsfeed.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.alexvanyo.sportsfeed.R

class MainSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_settings, rootKey)
    }
}