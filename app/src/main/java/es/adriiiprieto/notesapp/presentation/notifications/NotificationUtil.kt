package es.adriiiprieto.notesapp.presentation.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import es.adriiiprieto.notesapp.R
import es.adriiiprieto.notesapp.base.util.STRING_EMPTY
import es.adriiiprieto.notesapp.presentation.MainActivity

@Suppress("MemberVisibilityCanBePrivate")
class NotificationUtil(
    val context: Context,

    val channelId: String = DEFAULT_CHANNEL_ID,
    val channelName: String = STRING_EMPTY,
    val channelDescription: String = STRING_EMPTY,
    val notificationId: Int = NOTIFICATION_ID,

    val isClickable: Boolean = false,
    val setTitle: String = STRING_EMPTY,
    val setContent: String = STRING_EMPTY,
    val setLongContent: String = STRING_EMPTY,
    val priority: Int = NotificationCompat.PRIORITY_MAX,
    val category: String = NotificationCompat.CATEGORY_REMINDER,
    val timeOut: Long? = null,
    val isColorAccent: Boolean = true,
    val idColorized: Boolean = true,
    val drawableBig: Int? = null,
    val drawableMedium: Int? = null,
    val drawableSmall: Int? = android.R.drawable.ic_menu_save,
    val sound: Boolean = false,
    val vibrate: Boolean = false,
    val led: Boolean = false
) {

    companion object {
        const val DEFAULT_CHANNEL_ID = "DefaultChannelId"
        const val NOTIFICATION_ID = 1000
    }

    private var redirectIntent: PendingIntent? = null

    init {
        // Open the app if the notification is clickable
        if (isClickable) {
            redirectIntent = openActivityWhenPressNotification()
        }

        // Create notification
        val builder = createNotification()

        // Create channel and show notification
        showNotification(channelId, channelName, channelDescription, builder)
    }

    private fun openActivityWhenPressNotification(): PendingIntent {
        return PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP }, PendingIntent.FLAG_ONE_SHOT)
    }

    private fun createNotification(): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, channelId)
        builder.setDefaults(Notification.DEFAULT_ALL)
        builder.priority = priority
        builder.setCategory(category)

        if (isClickable) {
            builder.setContentIntent(redirectIntent)
        }

        if (drawableSmall != null) {
            builder.setSmallIcon(drawableSmall)
        }

        if (setTitle.isNotEmpty()) {
            builder.setContentTitle(setTitle)
        }

        if (setContent.isNotEmpty()) {
            builder.setContentText(setContent)
        }

        if (setLongContent.isNotEmpty()) {
            builder.setStyle(NotificationCompat.BigTextStyle().bigText(setLongContent))
        }

        builder.setAutoCancel(isClickable)

        if (timeOut != null) {
            builder.setTimeoutAfter(timeOut)
        }

        if (isColorAccent) {
            builder.color = ContextCompat.getColor(context, R.color.design_default_color_primary)
        }

        builder.setColorized(idColorized)

        if (drawableSmall != null) {
            builder.setSmallIcon(drawableSmall)
        }

        if (drawableMedium != null) {
            val image = getBitmap(drawableMedium)
            if (image != null) {
                builder.setLargeIcon(image)
            }
        }

        if (drawableBig != null) {
            val image = getBitmap(drawableBig)
            if (image != null) {
                builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
            }
        }

        if (sound) {
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        }

        if (vibrate) {
            builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        }

        if (led) {
            builder.setLights(Color.RED, 3000, 3000)
        }

        return builder
    }

    private fun getBitmap(drawableRes: Int): Bitmap? {
        return try {
            val drawable = ContextCompat.getDrawable(context, drawableRes)
            val canvas = Canvas()
            val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            canvas.setBitmap(bitmap)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    private fun showNotification(channelId: String, channelName: String, channelDescription: String, builder: NotificationCompat.Builder) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            channel.description = channelDescription

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, builder.build())
    }

}