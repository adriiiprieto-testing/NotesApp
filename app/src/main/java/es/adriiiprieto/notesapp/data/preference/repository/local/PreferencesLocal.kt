package es.adriiiprieto.notesapp.data.preference.repository.local

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesLocal @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val KEY_DATA_BOOLEAN = "KEY_DATA_BOOLEAN"
        const val KEY_DATA_STRING = "KEY_DATA_STRING"
        const val KEY_DATA_INTEGER = "KEY_DATA_INTEGER"
    }

    fun setKeyDataBoolean(value: Boolean) = sharedPreferences.edit().putBoolean(KEY_DATA_BOOLEAN, value).apply()

    fun getKeyDataBoolean(): Boolean = sharedPreferences.getBoolean(KEY_DATA_BOOLEAN, false)

    fun setKeyDataInteger(value: Int) = sharedPreferences.edit().putInt(KEY_DATA_INTEGER, value).apply()

    fun getKeyDataInteger(): Int = sharedPreferences.getInt(KEY_DATA_INTEGER, 0)

    fun setKeyDataString(value: String) = sharedPreferences.edit().putString(KEY_DATA_STRING, value).apply()

    fun getKeyDataString(): String = sharedPreferences.getString(KEY_DATA_STRING, "") ?: ""
}