package com.example.myapplication

import android.content.Intent
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
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val sentinelPackageName = "com.krishpinto.sentinelv2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SentinelLauncherHome(
                        modifier = Modifier.padding(innerPadding),
                        onOpenSentinel = { openSentinelApp() },
                        onOpenVault = { openSentinelApp() }
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


    @Composable
    fun SentinelLauncherHome(
        modifier: Modifier = Modifier,
        onOpenSentinel: () -> Unit,
        onOpenVault: () -> Unit
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

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Recent Alert",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFB91C1C)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Suspicious banking link detected from clipboard activity.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF334155)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

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
}
