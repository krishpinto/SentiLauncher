package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import java.net.URLEncoder

class SentinelNotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) return

        val extras = sbn.notification.extras
        val title = extras.getString("android.title") ?: ""
        val text = extras.getCharSequence("android.text")?.toString() ?: ""
        val packageName = sbn.packageName ?: ""

        if (title.isBlank() && text.isBlank()) return

        val combined = listOf(title, text)
            .filter { it.isNotBlank() }
            .joinToString(" - ")

        val encodedContent = URLEncoder.encode(combined, "UTF-8")
        val encodedLabel = URLEncoder.encode(packageName, "UTF-8")

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "sentinel://ingest?sourcetype=SMS&label=$encodedLabel&content=$encodedContent"
            )
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            startActivity(intent)
        } catch (_: Exception) {
        }
    }
}


