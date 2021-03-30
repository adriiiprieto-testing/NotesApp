package es.adriiiprieto.notesapp.domain.repository

import es.adriiiprieto.notesapp.domain.model.NoteDomainModel

interface NoteRepository {

    suspend fun getAll(): List<NoteDomainModel>

    suspend fun insert(note: NoteDomainModel)

    suspend fun update(note: NoteDomainModel)

    suspend fun delete(note: NoteDomainModel)

}