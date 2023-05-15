package com.harunturkoglu.cryptoappsample.data.local

import com.harunturkoglu.cryptoappsample.ext.EMPTY
import com.harunturkoglu.cryptoappsample.utils.SharedPreferencesManager
import javax.inject.Inject

class LocalDataManager @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) {

    fun getCurrentUserEmail(email: String) {
        sharedPreferencesManager.getString(email, String.EMPTY)
    }

    fun setCurrentUserEmail(email: String) {
        sharedPreferencesManager.putString(PreferencesKeyConst.USER_EMAIL, email)
    }

}