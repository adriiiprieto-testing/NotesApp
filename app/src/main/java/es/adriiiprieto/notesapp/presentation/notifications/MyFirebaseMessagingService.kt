package es.adriiiprieto.notesapp.presentation.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import es.adriiiprieto.notesapp.R
import es.adriiiprieto.notesapp.base.util.STRING_EMPTY
import es.adriiiprieto.notesapp.presentation.notifications.NotificationConstants.FB_CHANNEL_ID
import es.adriiiprieto.notesapp.presentation.notifications.NotificationConstants.FB_NOTIFICATION_ID

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            showNotification(it.title, it.body)
        }
    }

    private fun showNotification(messageTitle: String?, messageBody: String?) {
        NotificationUtil(
            context = this,
            isClickable = true,
            channelId = FB_CHANNEL_ID,
            channelName = getString(R.string.myFirebaseMessagingServiceNotificationName),
            channelDescription = getString(R.string.myFirebaseMessagingServiceNotificationDescription),
            setTitle = messageTitle ?: STRING_EMPTY,
            setContent = messageBody ?: STRING_EMPTY,
            setLongContent = messageBody ?: STRING_EMPTY,
            notificationId = FB_NOTIFICATION_ID
        )
    }

    companion object {
        private const val TAG = "MyFirebaseMessagingSer"
    }


}