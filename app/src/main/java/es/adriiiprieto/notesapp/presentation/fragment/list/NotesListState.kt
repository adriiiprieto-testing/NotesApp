package es.adriiiprieto.notesapp.presentation.fragment.list

import es.adriiiprieto.notesapp.base.BaseViewState
import es.adriiiprieto.notesapp.domain.model.NoteDomainModel

data class NotesListState(
    val notesList: List<NoteDomainModel> = listOf()
) : BaseViewState()