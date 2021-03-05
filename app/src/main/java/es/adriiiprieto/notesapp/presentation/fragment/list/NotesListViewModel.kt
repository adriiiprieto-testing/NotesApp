package es.adriiiprieto.notesapp.presentation.fragment.list

import dagger.hilt.android.lifecycle.HiltViewModel
import es.adriiiprieto.notesapp.base.BaseViewModel
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel
import es.adriiiprieto.notesapp.domain.repository.NoteRepository
import es.adriiiprieto.notesapp.domain.repository.PreferencesRepository
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(private val noteRepository: NoteRepository, private val preferencesRepository: PreferencesRepository) : BaseViewModel<NotesListState>() {

    override val defaultState: NotesListState = NotesListState()

    override fun onStartFirstTime() {
    }

    override fun onResume() {
        requestInformation()
    }

    fun requestInformation(){
        executeCoroutines({
            val notes = noteRepository.getAll()

            checkDataState { state ->
                updateToNormalState(state.copy(notesList = notes))
            }
        }, {

        })
    }

    fun onActionDeleteNote(item: NoteDomainModel) {
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
}