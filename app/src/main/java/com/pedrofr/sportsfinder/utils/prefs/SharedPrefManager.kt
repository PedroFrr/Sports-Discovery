package com.pedrofr.sportsfinder.utils.prefs

import android.content.Context
import android.content.SharedPreferences
import com.pedrofr.sportsfinder.utils.SPORTS_SHARED_PREFS
import com.pedrofr.sportsfinder.utils.USER_ID

class SharedPrefManager(context: Context){

    private val prefs = context.getSharedPreferences(SPORTS_SHARED_PREFS, Context.MODE_PRIVATE)

    fun setLoggedInUserId(userId: String?){ prefs.edit().putString(USER_ID, userId).apply() }

    fun getLoggedInUserId() = prefs.getString(USER_ID, "") ?: "1"

    fun clearLoggedInUserId() {
        prefs.edit().putString(USER_ID, "").apply()
    }


}