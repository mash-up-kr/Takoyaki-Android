package org.mashup.takoyaki.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject

class SharedPreferencesUtil @Inject constructor(context: Context) {
    private val preference: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    fun getString(key: String, defaultValue: String = "") = preference.getString(key, defaultValue)

    fun getFloat(key: String, defaultValue: Float = 0.0f) = preference.getFloat(key, defaultValue)

    fun putString(key: String, value: String) {
        preference.edit().putString(key, value).apply()
    }

    fun putFloat(key: String, value: Float) {
        preference.edit().putFloat(key, value).apply()
    }


}