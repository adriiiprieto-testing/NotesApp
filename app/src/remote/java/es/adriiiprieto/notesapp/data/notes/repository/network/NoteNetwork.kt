package es.adriiiprieto.notesapp.data.notes.repository.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import es.adriiiprieto.notesapp.data.notes.model.NoteFirestoreDataModel
import es.adriiiprieto.notesapp.data.notes.repository.network.FirestoreConstants.COLLECTION_NOTES
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class NoteNetwork @Inject constructor(private val db: FirebaseFirestore) {

    suspend fun getAll(): List<NoteFirestoreDataModel> {
        val result = db.collection(COLLECTION_NOTES).get().await()
        val list: MutableList<NoteFirestoreDataModel> = mutableListOf()
        result.documents.forEach { document ->
            document.toObject(NoteFirestoreDataModel::class.java)?.let { list.add(it) }
        }
        return list
    }

    suspend fun insert(note: NoteFirestoreDataModel) {
        val number = Random().nextInt()
        db.collection(COLLECTION_NOTES).document(number.toString()).set(note.copy(uid = number)).await()
    }

    suspend fun update(note: NoteFirestoreDataModel) {
        db.collection(COLLECTION_NOTES).document(note.uid.toString()).set(note, SetOptions.merge()).await()
    }

    suspend fun delete(note: NoteFirestoreDataModel) {
        db.collection(COLLECTION_NOTES).document(note.uid.toString()).delete().await()
    }

}