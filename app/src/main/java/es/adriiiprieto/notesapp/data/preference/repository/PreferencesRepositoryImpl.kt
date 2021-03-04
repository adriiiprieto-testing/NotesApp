package es.adriiiprieto.notesapp.data.preference.repository

import es.adriiiprieto.notesapp.data.preference.repository.local.PreferencesLocal
import es.adriiiprieto.notesapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(private val local: PreferencesLocal) : PreferencesRepository {

    override fun setKeyDataBoolean(value: Boolean) {
        local.setKeyDataBoolean(true)
    }

    override fun getKeyDataBoolean(): Boolean {
        return local.getKeyDataBoolean()
    }

    override fun setKeyDataInteger(value: Int) {
        local.setKeyDataInteger(123)
    }

    override fun getKeyDataInteger(): Int {
        return local.getKeyDataInteger()
    }

    override fun setKeyDataString(value: String) {
        local.setKeyDataString("Sample of text")
    }

    override fun getKeyDataString(): String {
        return local.getKeyDataString()
    }

}