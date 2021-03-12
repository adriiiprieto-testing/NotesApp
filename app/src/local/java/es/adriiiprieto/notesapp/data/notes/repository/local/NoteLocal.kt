package es.adriiiprieto.notesapp.data.notes.repository.local

import es.adriiiprieto.notesapp.data.notes.model.NoteEntityDataModel
import javax.inject.Inject

class NoteLocal @Inject constructor(private val dao: NoteDAO) {

    suspend fun getAll(): List<NoteEntityDataModel> {
        return dao.getAll()
    }

    suspend fun insert(note: NoteEntityDataModel) {
        dao.insert(note)
    }

    suspend fun update(note: NoteEntityDataModel) {
        dao.update(note)
    }

    suspend fun delete(note: NoteEntityDataModel) {
        dao.delete(note)
    }

}