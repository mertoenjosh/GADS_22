package com.mertoenjosh.notekeeper

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log

class PseudoMessagingManager(private val context: Context) {
    private val tag = this::class.simpleName

    private val connectionCallbackMilliseconds = 5000L
    private val postHandler = Handler(Looper.getMainLooper())

    fun connect(connectionCallback: (PseudoMessagingConnection) -> Unit) {
        Log.d(TAG, "Initiating connection...")
        postHandler.postDelayed(
                {
                    Log.d(TAG, "Connection established")
                    connectionCallback(PseudoMessagingConnection())
                },
                connectionCallbackMilliseconds)
    }

    companion object {
        private const val TAG = "PseudoMessagingManagerTAG"
    }
}

class PseudoMessagingConnection {
    private val tag = this::class.simpleName

    fun send(message: String) {
        Log.d(TAG, message)
    }

    fun disconnect() {
        Log.d(TAG, "Disconnected")
    }

    companion object {
        private const val TAG = "PseudoMessagingManagerTAG"
    }
}