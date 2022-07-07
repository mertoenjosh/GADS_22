package com.mertoenjosh.notekeeper

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class NoteGetTogetherHelper(val context: Context, private val lifecycle: Lifecycle): LifecycleObserver {
    init {
        lifecycle.addObserver(this)
    }

    private var currentLat = 0.0
    private var currentLon = 0.0

    private val locationManager = PseudoLocationManager(context) { lat, lon ->
        currentLat = lat
        currentLon = lon

        Log.d(TAG, "Location callback Lat:$currentLat Lon:$currentLon ")
    }

    private val msgManager = PseudoMessagingManager(context)
    private var msgConnection: PseudoMessagingConnection? = null

    fun sendMessage(note: NoteInfo) {
        val message = "$currentLat | $currentLon | ${note.title} | ${note.course?.title}"
        msgConnection?.send(message)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startHandler() {
        locationManager.start()
        msgManager.connect { connection ->
            Log.d(TAG, "startHandler: Lifecycle state-> ${lifecycle.currentState}")
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                msgConnection = connection
            else
                connection.disconnect()
        }
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopHandler() {
        Log.d(TAG, "stopHandler: Stop")
        locationManager.stop()
        msgConnection?.disconnect()
    }

    companion object {
        private const val TAG = "NoteGetTogetherHelperTAG"
    }

}