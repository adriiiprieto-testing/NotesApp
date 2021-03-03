package es.adriiiprieto.notesapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adriiiprieto.notesapp.data.config.NotesDatabase
import es.adriiiprieto.notesapp.data.notes.repository.NoteRepositoryImpl
import es.adriiiprieto.notesapp.data.notes.repository.local.NoteLocal
import es.adriiiprieto.notesapp.domain.repository.NoteRepository

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideNoteRepository(notesDatabase: NotesDatabase): NoteRepository = NoteRepositoryImpl(NoteLocal(notesDatabase.loadDatabase().noteDao()))

}