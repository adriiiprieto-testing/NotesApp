package es.adriiiprieto.notesapp.presentation.fragment.form

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel
import es.adriiiprieto.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltWorker
class SaveNoteWorker @AssistedInject constructor(@Assisted appContext: Context, @Assisted workerParams: WorkerParameters, private val repository: NoteRepository) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        // Get params
        val title = inputData.getString("title") ?: return Result.failure()
        val body = inputData.getString("body") ?: return Result.failure()
        val id = inputData.getInt("id", 0)

        saveNote(NoteDomainModel(title = title, body = body, id = id))

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }

    private suspend fun saveNote(noteToSave: NoteDomainModel) {
        Log.e("SaveNoteWorker", "I am doing something")

        withContext(Dispatchers.IO) {
            repository.update(noteToSave)
        }
    }

}