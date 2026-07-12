package com.example.whatsappcallrecorder.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.whatsappcallrecorder.R
import com.example.whatsappcallrecorder.receiver.PhoneStateReceiver
import com.example.whatsappcallrecorder.utils.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CallDetectionService : Service() {

    companion object {
        private const val TAG = "CallDetectionService"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "call_recording_channel"
    }

    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false
    private var callStartTime: Long = 0
    private val phoneStateReceiver = PhoneStateReceiver()
    private val serviceScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")
        NotificationHelper.createNotificationChannel(this, CHANNEL_ID)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service started")
        
        // Register phone state receiver
        val intentFilter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(phoneStateReceiver, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(phoneStateReceiver, intentFilter)
        }

        // Start foreground notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("WhatsApp Call Recorder")
            .setContentText("Monitoring for calls...")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service destroyed")
        unregisterReceiver(phoneStateReceiver)
        stopRecording()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    fun startRecording(contactName: String = "Unknown") {
        if (isRecording) {
            Log.w(TAG, "Already recording")
            return
        }

        try {
            callStartTime = System.currentTimeMillis()
            val recordingFile = createRecordingFile()
            
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.VOICE_CALL)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioSamplingRate(44100)
                setAudioEncodingBitRate(128000)
                setOutputFile(recordingFile.absolutePath)
                prepare()
                start()
            }

            isRecording = true
            Log.d(TAG, "Recording started: ${recordingFile.absolutePath}")
            
            // Show recording notification
            sendRecordingNotification(contactName, recordingFile)

        } catch (e: Exception) {
            Log.e(TAG, "Error starting recording", e)
            isRecording = false
        }
    }

    fun stopRecording(): String? {
        if (!isRecording || mediaRecorder == null) {
            return null
        }

        return try {
            mediaRecorder?.apply {
                try {
                    stop()
                    release()
                } catch (e: Exception) {
                    Log.e(TAG, "Error stopping recorder", e)
                }
            }
            mediaRecorder = null
            isRecording = false
            Log.d(TAG, "Recording stopped")
            
            val duration = (System.currentTimeMillis() - callStartTime) / 1000
            "Recording saved (${duration}s)"
        } catch (e: Exception) {
            Log.e(TAG, "Error in stopRecording", e)
            null
        }
    }

    private fun createRecordingFile(): File {
        val recordingDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "WhatsAppCallRecorder"
        )

        if (!recordingDir.exists()) {
            recordingDir.mkdirs()
        }

        val timeStamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(Date())
        return File(recordingDir, "call_${timeStamp}.m4a")
    }

    private fun sendRecordingNotification(contactName: String, file: File) {
        serviceScope.launch {
            val notification = NotificationCompat.Builder(this@CallDetectionService, CHANNEL_ID)
                .setContentTitle("Recording WhatsApp Call")
                .setContentText("Contact: $contactName")
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            try {
                startForeground(NOTIFICATION_ID, notification)
            } catch (e: Exception) {
                Log.e(TAG, "Error updating notification", e)
            }
        }
    }
}
