package es.adriiiprieto.notesapp.data.config

import androidx.room.Database
import androidx.room.RoomDatabase
import es.adriiiprieto.notesapp.data.notes.model.NoteEntityDataModel
import es.adriiiprieto.notesapp.data.notes.repository.local.NoteDAO

@Database(entities = [NoteEntityDataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
}