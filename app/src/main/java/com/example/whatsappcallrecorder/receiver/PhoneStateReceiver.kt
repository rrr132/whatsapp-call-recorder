package com.example.whatsappcallrecorder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import com.example.whatsappcallrecorder.service.CallDetectionService

class PhoneStateReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "PhoneStateReceiver"
    }

    private var previousState = TelephonyManager.CALL_STATE_IDLE
    private var service: CallDetectionService? = null

    override fun onReceive(context: Context, intent: Intent) {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val state = telephonyManager.callState

        Log.d(TAG, "Phone state changed: $state (previous: $previousState)")

        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                handleIncomingCall(context, intent)
            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                handleCallOffHook(context)
            }
            TelephonyManager.CALL_STATE_IDLE -> {
                handleCallIdle(context)
            }
        }

        previousState = state
    }

    private fun handleIncomingCall(context: Context, intent: Intent) {
        val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) ?: "Unknown"
        Log.d(TAG, "Incoming call from: $incomingNumber")
        
        // Send notification to inform caller that we're recording
        sendConsentNotification(context, incomingNumber)
    }

    private fun handleCallOffHook(context: Context) {
        Log.d(TAG, "Call answered - starting recording")
        
        // Get the service and start recording
        val intent = Intent(context, CallDetectionService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }

        // You can also pass call details to service if needed
        // For now, we'll start recording in the service
    }

    private fun handleCallIdle(context: Context) {
        Log.d(TAG, "Call ended - stopping recording")
        
        val serviceIntent = Intent(context, CallDetectionService::class.java)
        // Stop the recording via intent
        serviceIntent.action = "STOP_RECORDING"
        context.startService(serviceIntent)
    }

    private fun sendConsentNotification(context: Context, contactNumber: String) {
        // This would send a message or notification to inform the caller
        // In a real app, you might send a WhatsApp message like:
        // "This call is being recorded for quality and training purposes"
        
        Log.d(TAG, "Consent notification should be sent to: $contactNumber")
        
        // TODO: Implement actual consent notification
        // This could be:
        // 1. A WhatsApp message (requires WhatsApp Business API)
        // 2. An in-app notification
        // 3. Voice announcement
    }
}
