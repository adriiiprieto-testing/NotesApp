package es.adriiiprieto.notesapp.domain.repository

interface PreferencesRepository {

    fun setKeyDataBoolean(value: Boolean)

    fun getKeyDataBoolean(): Boolean

    fun setKeyDataInteger(value: Int)

    fun getKeyDataInteger(): Int

    fun setKeyDataString(value: String)

    fun getKeyDataString(): String

}