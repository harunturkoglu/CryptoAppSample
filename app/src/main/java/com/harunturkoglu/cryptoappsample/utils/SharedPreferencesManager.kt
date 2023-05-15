package com.harunturkoglu.cryptoappsample.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.harunturkoglu.cryptoappsample.ext.EMPTY
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {
    private val editor = sharedPreferences.edit()

    fun getString(key: String, defaultValue: String = String.EMPTY) = sharedPreferences.getString(key, defaultValue)

    fun putString(key: String, value: String) {
        editor.putString(key,value)
        editor.apply()
    }
}