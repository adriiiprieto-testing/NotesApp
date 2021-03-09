package es.adriiiprieto.notesapp.presentation.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Inject

class AnalyticsHandler @Inject constructor(private val firebase: FirebaseAnalytics) {

    companion object {
        private const val TAG = "AnalyticsHandler"
    }

    fun trackScreen(screenName: String) {
        firebase.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }

    fun trackComponent(keyComponent: String, valueComponent: String) {
        firebase.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(keyComponent, valueComponent)
        }
    }


}