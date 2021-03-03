package es.adriiiprieto.notesapp.presentation.fragment.form

import dagger.hilt.android.lifecycle.HiltViewModel
import es.adriiiprieto.notesapp.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class NotesFormViewModel @Inject constructor(): BaseViewModel<NotesFormState>() {

    override val defaultState: NotesFormState = NotesFormState()

    override fun onStartFirstTime() {

    }
}