package es.adriiiprieto.notesapp.presentation.fragment.list

import dagger.hilt.android.lifecycle.HiltViewModel
import es.adriiiprieto.notesapp.base.BaseViewModel
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel
import es.adriiiprieto.notesapp.domain.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(private val repository: NoteRepository) : BaseViewModel<NotesListState>() {

    override val defaultState: NotesListState = NotesListState()

    override fun onStartFirstTime() {
    }

    override fun onResume() {
        executeCoroutines({
            val notes = repository.getAll()

            checkDataState { state ->
                updateToNormalState(state.copy(notesList = notes))
            }
        }, {

        })
    }

    fun onActionDeleteNote(item: NoteDomainModel) {
        executeCoroutines({
            repository.delete(item)
            val notes = repository.getAll()

            checkDataState { state ->
                updateToNormalState(state.copy(notesList = notes))
            }
        }, {

        })
    }
}