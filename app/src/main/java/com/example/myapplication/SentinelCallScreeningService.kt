package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import androidx.core.app.NotificationCompat
import java.net.URLEncoder

class SentinelCallScreeningService : CallScreeningService() {

    private val debugChannelId = "sentinel_call_debug_channel"

    override fun onScreenCall(callDetails: Call.Details) {
        val number = callDetails.handle?.schemeSpecificPart ?: "Unknown"

        createDebugChannel()
        showDebugNotification("CallScreening fired for: $number")

        val suspicious = true

        val response = CallResponse.Builder()
            .setDisallowCall(false)
            .setRejectCall(false)
            .setSkipCallLog(false)
            .setSkipNotification(false)
            .build()

        respondToCall(callDetails, response)

        if (suspicious) {
            val encodedContent = URLEncoder.encode(
                "CALL_SERVICE_TRIGGERED from $number",
                "UTF-8"
            )
            val encodedLabel = URLEncoder.encode(number, "UTF-8")

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "sentinel://ingest?sourcetype=CALL&label=$encodedLabel&content=$encodedContent"
                )
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            try {
                startActivity(intent)
                showDebugNotification("Sentinel deep link launched for: $number")
            } catch (e: Exception) {
                showDebugNotification("Deep link launch failed for: $number")
            }
        }
    }

    private fun createDebugChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                debugChannelId,
                "Sentinel Call Debug",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun showDebugNotification(message: String) {
        val notification = NotificationCompat.Builder(this, debugChannelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Sentinel Call Debug")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify((System.currentTimeMillis() % 100000).toInt(), notification)
    }
}
