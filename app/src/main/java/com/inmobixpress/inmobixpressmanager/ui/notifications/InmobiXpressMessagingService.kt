package com.inmobixpress.inmobixpressmanager.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.inmobixpress.inmobixpressmanager.MainActivity
import com.inmobixpress.inmobixpressmanager.R
import kotlin.random.Random

class InmobiXpressMessagingService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_NAME = "InmobiXpress Channel"
        const val CHANNEL_ID = "fcm_default_channel"
    }

    private val random = Random

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            Log.e("FCM", "${it.title} ${message.data}")
            sendNotification(it, message.data)
        }
    }

    private fun sendNotification(message: RemoteMessage.Notification, data: Map<String, String>) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        }
        intent.putExtra("id", data["id"])
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, FLAG_IMMUTABLE
        )
        val channelId = this.getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_launcher_home_foreground)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(random.nextInt(), notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Log.e("FCM", token)
        super.onNewToken(token)
    }
}