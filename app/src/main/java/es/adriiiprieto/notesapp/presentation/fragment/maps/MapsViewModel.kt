package es.adriiiprieto.notesapp.presentation.fragment.maps

import dagger.hilt.android.lifecycle.HiltViewModel
import es.adriiiprieto.notesapp.base.BaseViewModel
import es.adriiiprieto.notesapp.presentation.analytics.AnalyticsHandler
import es.adriiiprieto.notesapp.presentation.analytics.SCREEN_MAPS
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val analytics: AnalyticsHandler) : BaseViewModel<MapsState>() {

    override val defaultState: MapsState = MapsState()

    override fun onStartFirstTime() {
        analytics.trackScreen(SCREEN_MAPS)
    }
}