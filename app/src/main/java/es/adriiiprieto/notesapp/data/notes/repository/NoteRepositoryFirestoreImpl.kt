package es.adriiiprieto.notesapp.data.notes.repository

import es.adriiiprieto.notesapp.data.notes.model.toDataModel
import es.adriiiprieto.notesapp.data.notes.model.toDomainModel
import es.adriiiprieto.notesapp.data.notes.model.toFirebaseDataModel
import es.adriiiprieto.notesapp.data.notes.repository.local.NoteLocal
import es.adriiiprieto.notesapp.data.notes.repository.network.NoteNetwork
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel
import es.adriiiprieto.notesapp.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryFirestoreImpl @Inject constructor(private val network: NoteNetwork) : NoteRepository {

    override suspend fun getAll(): List<NoteDomainModel> {
        return network.getAll().map { it.toDomainModel() }
    }

    override suspend fun insert(note: NoteDomainModel) {
        network.insert(note.toFirebaseDataModel())
    }

    override suspend fun update(note: NoteDomainModel) {
        network.update(note.toFirebaseDataModel())
    }

    override suspend fun delete(note: NoteDomainModel) {
        network.delete(note.toFirebaseDataModel())
    }

}