package es.adriiiprieto.notesapp.presentation.fragment.list

import dagger.hilt.android.lifecycle.HiltViewModel
import es.adriiiprieto.notesapp.base.BaseViewModel
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel
import es.adriiiprieto.notesapp.domain.repository.NoteRepository
import es.adriiiprieto.notesapp.domain.repository.PreferencesRepository
import es.adriiiprieto.notesapp.presentation.analytics.*
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(private val noteRepository: NoteRepository, private val preferencesRepository: PreferencesRepository, private val analytics: AnalyticsHandler) : BaseViewModel<NotesListState>() {

    override val defaultState: NotesListState = NotesListState()

    override fun onStartFirstTime() {
        analytics.trackScreen(SCREEN_NOTES_LIST)
    }

    override fun onResume() {
        requestInformation()
    }

    fun requestInformation() {
        executeCoroutines({
            val notes = noteRepository.getAll()

            checkDataState { state ->
                updateToNormalState(state.copy(notesList = notes))
            }
        }, {

        })
    }

    fun onActionDeleteNote(item: NoteDomainModel) {
        analytics.trackComponent(COMPONENT_BUTTON, COMPONENT_BUTTON_DELETE_NOTE)

        preferencesRepository.setKeyDataBoolean(true)

        executeCoroutines({
            noteRepository.delete(item)
            val notes = noteRepository.getAll()

            checkDataState { state ->
                updateToNormalState(state.copy(notesList = notes))
            }
        }, {

        })
    }

    fun getDataCSV(): String {
        var textToWriteInFile = ""

        textToWriteInFile += "Índice,Identificador único,Título,Body\n"

        checkDataState { state ->
            state.notesList.forEachIndexed { index, noteDomainModel ->
                textToWriteInFile += "$index,${noteDomainModel.id},${noteDomainModel.title.replace(",", "")},${noteDomainModel.body.replace(",", "")}\n"
            }
        }

        return textToWriteInFile
    }

    fun onActionClickOnNewNoteButton() {
        analytics.trackComponent(COMPONENT_BUTTON, COMPONENT_BUTTON_NEW_NOTE)
    }

    fun onActionClickOnEditNoteButton() {
        analytics.trackComponent(COMPONENT_ITEM_LIST, COMPONENT_ITEM_LIST_EDIT_NOTE)
    }
}