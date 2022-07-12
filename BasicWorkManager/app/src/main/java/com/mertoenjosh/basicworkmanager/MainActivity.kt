package com.mertoenjosh.basicworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val workManager = WorkManager.getInstance(this)
    private lateinit var btnStartWork: Button
    private lateinit var btnWorkStatus: Button
    private lateinit var btnResetStatus: Button
    private lateinit var btnWorkUIThread: Button
    private lateinit var btnWorkFailed: Button
    private lateinit var btnWorkRetried: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStartWork = findViewById(R.id.btnStartWork)
        btnWorkStatus = findViewById(R.id.btnWorkStatus)
        btnResetStatus = findViewById(R.id.btnResetStatus)
        btnWorkUIThread = findViewById(R.id.btnWorkUIThread)
        btnWorkFailed = findViewById(R.id.btnWorkFailed)
        btnWorkRetried = findViewById(R.id.btnWorkRetried)

        btnStartWork.setOnClickListener{

            // JAVA way
//            val workRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
            // JAVA way
//            val data = Data.Builder()
//                .putString("WORK_MESSAGE", "Work Completed!")
//                .build()

            // define constraints
            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            // set data
            val data = workDataOf("WORK_MESSAGE" to "Work Completed!")

            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
                .setInputData(data)
                .setConstraints(constraints)
                .build()

            // set work manager to run periodically
            val periodicWorkRequest = PeriodicWorkRequestBuilder<SimpleWorker>(
                5, TimeUnit.MINUTES,
                1, TimeUnit.MINUTES
            ).build()

            workManager.enqueue(workRequest)
        }

        btnWorkStatus.setOnClickListener {
            val toast = Toast.makeText(this, "The work status is: ${WorkStatusSingleton.workMessage}", Toast.LENGTH_SHORT)
            toast.show()
        }

        btnResetStatus.setOnClickListener {
            WorkStatusSingleton.workComplete = false
        }

        btnWorkUIThread.setOnClickListener {
            Thread.sleep(10000)
            WorkStatusSingleton.workComplete = true
        }

        // Handle work failure

        btnWorkFailed.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<WorkerFail>().build()
            workManager.enqueue(workRequest)
            Log.d(TAG, "onCreate: Clicked")
        }

        btnWorkRetried.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<WorkerRetry>()
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    10,
                    TimeUnit.SECONDS
                ).build()

            workManager.enqueue(workRequest)
        }
    }
    
    companion object {
        private const val TAG = "MainActivityTAG"
    }
}