package es.adriiiprieto.notesapp.presentation.fragment.form

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import es.adriiiprieto.notesapp.base.BaseExtraData
import es.adriiiprieto.notesapp.base.BaseViewModel
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel
import es.adriiiprieto.notesapp.domain.repository.NoteRepository
import es.adriiiprieto.notesapp.presentation.analytics.AnalyticsHandler
import es.adriiiprieto.notesapp.presentation.analytics.SCREEN_NOTES_FORM
import es.adriiiprieto.notesapp.presentation.fragment.form.NotesFormFragment.Companion.CODE_ALL_RIGHT
import es.adriiiprieto.notesapp.presentation.fragment.form.NotesFormFragment.Companion.CODE_CONFIRM_UPDATE
import es.adriiiprieto.notesapp.presentation.fragment.form.NotesFormFragment.Companion.CODE_EXIT
import es.adriiiprieto.notesapp.presentation.fragment.form.NotesFormFragment.Companion.FIELD_KEY_ALL
import es.adriiiprieto.notesapp.presentation.fragment.form.NotesFormFragment.Companion.FIELD_KEY_BODY
import es.adriiiprieto.notesapp.presentation.fragment.form.NotesFormFragment.Companion.FIELD_KEY_TITLE
import javax.inject.Inject

@HiltViewModel
class NotesFormViewModel @Inject constructor(private val repository: NoteRepository, @ApplicationContext private val context: Context, private val analytics: AnalyticsHandler) : BaseViewModel<NotesFormState>() {

    override val defaultState: NotesFormState = NotesFormState()

    override fun onStartFirstTime() {
        analytics.trackScreen(SCREEN_NOTES_FORM)
    }

    fun setNote(inputNote: NoteDomainModel?) {
        inputNote?.let { noteToEdit ->
            updateToNormalState(NotesFormState(title = noteToEdit.title, body = noteToEdit.body, note = noteToEdit))
        }
    }

    fun onActionSetTitle(text: String) {
        checkDataState { state ->
            if (text != state.title) {
                updateDataState(state.copy(title = text))
            }
        }
    }

    fun onActionSetBody(text: String) {
        checkDataState { state ->
            if (text != state.body) {
                updateDataState(state.copy(body = text))
            }
        }
    }

    fun onActionSaveNote() {
        updateToLoadingState()

        checkDataState { state ->
            if (state.title.isNotEmpty() && state.body.isNotEmpty()) {

                executeCoroutines({

                    if (state.note == null) {
                        repository.insert(NoteDomainModel(title = state.title, body = state.body))

                        updateToLoadingState(BaseExtraData(CODE_ALL_RIGHT))
                    } else {
                        updateToLoadingState(BaseExtraData(CODE_CONFIRM_UPDATE))
                    }

                }, {})

            } else {
                val msg = when {
                    state.title.isEmpty() && state.body.isEmpty() -> FIELD_KEY_ALL
                    state.title.isEmpty() -> FIELD_KEY_TITLE
                    state.body.isEmpty() -> FIELD_KEY_BODY
                    else -> ""
                }
                updateToErrorState(FieldErrorException(msg))
            }
        }
    }

    fun onActionUpdateNote() {
        checkDataState { state ->

            if (state.note != null) {
/*
                // Set constraints to the work
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .build()

                // Create and configure the work
                val saveWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<SaveNoteWorker>()
                    .addTag("saveInfo")
                    .setInputData(
                        workDataOf(
                            "title" to state.title,
                            "body" to state.body,
                            "id" to state.note.id
                        )
                    )
                    .setConstraints(constraints)
                    .build()

                // Launch the work
                WorkManager.getInstance(context).enqueue(saveWorkRequest)
*/

                executeCoroutines({
//                    repository.update(NoteDomainModel(state.note.id, state.title, state.body))
                    repository.update(state.note.copy(title = state.title, body = state.body))

                    updateToLoadingState(BaseExtraData(CODE_EXIT))
                }, {})
            }
        }
    }

}