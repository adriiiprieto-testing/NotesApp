package es.adriiiprieto.notesapp.data.notes.model

import com.google.firebase.firestore.Exclude
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel

data class NoteFirestoreDataModel(
    val uid: Int = 0,
    val title: String = "",
    val body: String = ""
)

fun NoteFirestoreDataModel.toDomainModel(): NoteDomainModel {
    return NoteDomainModel(uid, title, body)
}

fun NoteDomainModel.toFirebaseDataModel(): NoteFirestoreDataModel {
    return NoteFirestoreDataModel(id, title, body)
}
