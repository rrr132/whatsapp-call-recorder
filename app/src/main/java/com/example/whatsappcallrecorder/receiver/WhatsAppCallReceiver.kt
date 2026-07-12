package com.example.whatsappcallrecorder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.whatsappcallrecorder.service.CallDetectionService

class WhatsAppCallReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "WhatsAppCallReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "WhatsApp call broadcast received: ${intent.action}")

        when (intent.action) {
            "com.whatsapp.call.INCOMING" -> {
                handleIncomingCall(context, intent)
            }
            "com.whatsapp.call.OUTGOING" -> {
                handleOutgoingCall(context, intent)
            }
        }
    }

    private fun handleIncomingCall(context: Context, intent: Intent) {
        val contactName = intent.getStringExtra("contact_name") ?: "Unknown Contact"
        val contactNumber = intent.getStringExtra("contact_number") ?: "Unknown"

        Log.d(TAG, "Incoming WhatsApp call from: $contactName ($contactNumber)")

        // Send consent notification
        sendConsentMessage(context, contactName)

        // Start recording service
        val serviceIntent = Intent(context, CallDetectionService::class.java)
        serviceIntent.putExtra("contact_name", contactName)
        serviceIntent.putExtra("contact_number", contactNumber)
        serviceIntent.action = "START_RECORDING"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }

    private fun handleOutgoingCall(context: Context, intent: Intent) {
        val contactName = intent.getStringExtra("contact_name") ?: "Unknown Contact"
        val contactNumber = intent.getStringExtra("contact_number") ?: "Unknown"

        Log.d(TAG, "Outgoing WhatsApp call to: $contactName ($contactNumber)")

        // Start recording service
        val serviceIntent = Intent(context, CallDetectionService::class.java)
        serviceIntent.putExtra("contact_name", contactName)
        serviceIntent.putExtra("contact_number", contactNumber)
        serviceIntent.action = "START_RECORDING"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }

    private fun sendConsentMessage(context: Context, contactName: String) {
        // This sends a notification/message that the call is being recorded
        Log.d(TAG, "Sending consent notification to $contactName")
        
        // Implementation options:
        // 1. Send WhatsApp message: "This call is being recorded"
        // 2. Show system notification
        // 3. Play voice message during call
        
        // For now, we'll log it - actual implementation depends on WhatsApp Business API access
    }
}
