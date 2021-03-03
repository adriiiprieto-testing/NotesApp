package es.adriiiprieto.notesapp.data.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel

@Entity(tableName = "notes")
data class NoteEntityDataModel(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String
)

fun NoteEntityDataModel.toDomainModel(): NoteDomainModel {
    return NoteDomainModel(uid, title, body)
}

fun NoteDomainModel.toDataModel(): NoteEntityDataModel {
    return NoteEntityDataModel(id, title, body)
}
