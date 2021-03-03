package es.adriiiprieto.notesapp.domain.model

import java.io.Serializable

data class NoteDomainModel(
    val id: Int = 0,
    val title: String = "",
    val body: String = ""
): Serializable