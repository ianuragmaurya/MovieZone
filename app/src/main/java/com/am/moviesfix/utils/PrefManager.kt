package com.am.moviesfix.utils

import android.content.Context

class PrefManager(context: Context) {
    private val prefs = context.getSharedPreferences("movie_pref", Context.MODE_PRIVATE)

    companion object {
        private const val IS_DARK_MODE = "is_dark_mode"

    }
    fun saveDarkMode(isDark : Boolean) {
        prefs.edit().putBoolean(IS_DARK_MODE, isDark).apply()
    }
    fun isDarkMode() : Boolean {
        return prefs.getBoolean(IS_DARK_MODE, false)
    }

}