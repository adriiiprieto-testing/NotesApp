package es.adriiiprieto.notesapp.presentation.fragment.form

import es.adriiiprieto.notesapp.base.BaseViewState
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel

data class NotesFormState(
    val title: String = "",
    val body: String = "",
    val note: NoteDomainModel? = null
) : BaseViewState()