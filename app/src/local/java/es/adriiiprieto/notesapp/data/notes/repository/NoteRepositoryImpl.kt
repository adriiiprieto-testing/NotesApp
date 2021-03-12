package es.adriiiprieto.notesapp.data.notes.repository

import es.adriiiprieto.notesapp.data.notes.model.toDataModel
import es.adriiiprieto.notesapp.data.notes.model.toDomainModel
import es.adriiiprieto.notesapp.data.notes.repository.local.NoteLocal
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel
import es.adriiiprieto.notesapp.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val local: NoteLocal) : NoteRepository {

    override suspend fun getAll(): List<NoteDomainModel> {
        return local.getAll().map { it.toDomainModel() }
    }

    override suspend fun insert(note: NoteDomainModel) {
        local.insert(note.toDataModel())
    }

    override suspend fun update(note: NoteDomainModel) {
        local.update(note.toDataModel())
    }

    override suspend fun delete(note: NoteDomainModel) {
        local.delete(note.toDataModel())
    }

}