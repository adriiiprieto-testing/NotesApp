package es.adriiiprieto.notesapp.data.notes.repository.local

import androidx.room.*
import es.adriiiprieto.notesapp.data.notes.model.NoteEntityDataModel

@Dao
interface NoteDAO {

    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<NoteEntityDataModel>

    @Insert
    suspend fun insert(note: NoteEntityDataModel)

    @Update
    suspend fun update(note: NoteEntityDataModel)

    @Delete
    suspend fun delete(note: NoteEntityDataModel)

}