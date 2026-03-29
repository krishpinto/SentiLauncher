package com.example.myapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.role.RoleManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val sentinelPackageName = "com.krishpinto.sentinelv2"
    private val channelId = "sentinel_test_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        createNotificationChannel()

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SentinelLauncherHome(
                        modifier = Modifier.padding(innerPadding),
                        onOpenSentinel = { openSentinelApp() },
                        onOpenVault = { openSentinelApp() },
                        onSendTestNotification = { sendTestScamNotification() },
                        onEnableCallScreening = { requestCallScreeningRole() }
                    )
                }
            }
        }
    }

    private fun openSentinelApp() {
        try {
            val intent = Intent().apply {
                setClassName(
                    "com.krishpinto.sentinelv2",
                    "com.krishpinto.sentinelv2.MainActivity"
                )
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Sentinel app could not be opened.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun requestCallScreeningRole() {
        Toast.makeText(this, "Enable Call Screening tapped", Toast.LENGTH_SHORT).show()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(RoleManager::class.java)

            if (roleManager == null) {
                Toast.makeText(
                    this,
                    "RoleManager is null on this device.",
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            if (!roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING)) {
                Toast.makeText(
                    this,
                    "Call screening role is not available on this device.",
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            if (roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {
                Toast.makeText(
                    this,
                    "Call screening is already enabled.",
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            Toast.makeText(
                this,
                "Opening call screening role request...",
                Toast.LENGTH_LONG
            ).show()

            val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
            startActivity(intent)
        } else {
            Toast.makeText(
                this,
                "Call screening requires Android 10 or newer.",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Sentinel Test Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun sendTestScamNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
                return
            }
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("SBI Security Alert")
            .setContentText("Urgent: Verify your bank account now or it will be suspended. Click here immediately.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(2001, notification)

        Toast.makeText(this, "Test scam notification sent.", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun SentinelLauncherHome(
    modifier: Modifier = Modifier,
    onOpenSentinel: () -> Unit,
    onOpenVault: () -> Unit,
    onSendTestNotification: () -> Unit,
    onEnableCallScreening: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Sentinel",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Text(
            text = "Launcher Security Shell",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF475569)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Protection Active",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Threats flagged today: 2",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF334155)
                )
                Text(
                    text = "Passive monitoring: Enabled",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF334155)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onSendTestNotification,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
        ) {
            Text(text = "Send Test Scam Notification")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onEnableCallScreening,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669))
        ) {
            Text(text = "Enable Call Screening")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onOpenSentinel,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
        ) {
            Text(text = "Open Sentinel")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onOpenVault,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2E8F0))
        ) {
            Text(text = "Threat Vault", color = Color(0xFF0F172A))
        }
    }
}
