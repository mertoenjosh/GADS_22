package com.mertoenjosh.workchains

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkContinuation
import androidx.work.WorkManager
import androidx.work.workDataOf

class MainActivity : AppCompatActivity() {
    private val workManager = WorkManager.getInstance(this)

    private lateinit var btnSingleChainSucceed: Button
    private lateinit var btnSingleChainFail: Button
    private lateinit var btnGroupChainSucceed: Button
    private lateinit var btnGroupChainFail: Button
    private lateinit var btnMultipleChainSucceed: Button
    private lateinit var btnMultipleChainFail: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSingleChainSucceed = findViewById(R.id.btnSingleChainSucceed)
        btnSingleChainFail = findViewById(R.id.btnSingleChainFail)
        btnGroupChainSucceed = findViewById(R.id.btnGroupChainSucceed)
        btnGroupChainFail = findViewById(R.id.btnGroupChainFail)
        btnMultipleChainSucceed = findViewById(R.id.btnMultipleChainSucceed)
        btnMultipleChainFail = findViewById(R.id.btnMultipleChainFail)

        btnSingleChainSucceed.setOnClickListener {
            val objectDetectionWorkRequest = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()
            val networkRequestWorkRequest = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()
            val databaseWriteWorkRequest = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()

            workManager.beginWith(objectDetectionWorkRequest)
                .then(networkRequestWorkRequest)
                .then(databaseWriteWorkRequest)
                .enqueue()
        }

        btnSingleChainFail.setOnClickListener {
            val objectDetectionWorkRequest = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()
            val networkRequestWorkRequest = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                .setInputData(workDataOf("SUCCESS" to false))
                .build()
            val databaseWriteWorkRequest = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()

            workManager.beginWith(objectDetectionWorkRequest)
                .then(networkRequestWorkRequest)
                .then(databaseWriteWorkRequest)
                .enqueue()
        }

        btnGroupChainSucceed.setOnClickListener {
            val objectDetectionWorkRequest1 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()
            val objectDetectionWorkRequest2 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()
            val networkRequestWorkRequest = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()
            val databaseWriteWorkRequest = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()

            workManager.beginWith(listOf(objectDetectionWorkRequest1, objectDetectionWorkRequest2))
                .then(networkRequestWorkRequest)
                .then(databaseWriteWorkRequest)
                .enqueue()
        }

        btnGroupChainFail.setOnClickListener {
            val objectDetectionWorkRequest1 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()
            val objectDetectionWorkRequest2 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to false))
                .build()
            val networkRequestWorkRequest = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()
            val databaseWriteWorkRequest = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                .setInputData(workDataOf("SUCCESS" to true))
                .build()

            workManager.beginWith(listOf(objectDetectionWorkRequest1, objectDetectionWorkRequest2))
                .then(networkRequestWorkRequest)
                .then(databaseWriteWorkRequest)
                .enqueue()
        }

        btnMultipleChainSucceed.setOnClickListener {

            val objectDetectionWorkRequest1 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                .build()
            val objectDetectionWorkRequest2 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                .build()
            val networkRequestWorkRequest1 = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                .build()
            val networkRequestWorkRequest2 = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                .build()
            val databaseWriteWorkRequest1 = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                .build()
            val databaseWriteWorkRequest2 = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                .build()

            val recommendation1 = workManager.beginWith(objectDetectionWorkRequest1)
                .then(networkRequestWorkRequest1)
                .then(databaseWriteWorkRequest1)

            val recommendation2 = workManager.beginWith(objectDetectionWorkRequest2)
                .then(networkRequestWorkRequest2)
                .then(databaseWriteWorkRequest2)

            val root = WorkContinuation.combine(listOf(recommendation1, recommendation2))

            root.enqueue()
        }

        btnMultipleChainFail.setOnClickListener {

            val objectDetectionWorkRequest1 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                .build()
            val objectDetectionWorkRequest2 = OneTimeWorkRequestBuilder<ObjectDetectionWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                .build()
            val networkRequestWorkRequest1 = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                .build()
            val networkRequestWorkRequest2 = OneTimeWorkRequestBuilder<NetworkRequestWorker>()
                .setInputData(workDataOf("SUCCESS" to false, "NAME" to "TWO"))
                .build()
            val databaseWriteWorkRequest1 = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "ONE"))
                .build()
            val databaseWriteWorkRequest2 = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
                .setInputData(workDataOf("SUCCESS" to true, "NAME" to "TWO"))
                .build()

            val recommendation1 = workManager.beginWith(objectDetectionWorkRequest1)
                .then(networkRequestWorkRequest1)
                .then(databaseWriteWorkRequest1)

            val recommendation2 = workManager.beginWith(objectDetectionWorkRequest2)
                .then(networkRequestWorkRequest2)
                .then(databaseWriteWorkRequest2)

            val root = WorkContinuation.combine(listOf(recommendation1, recommendation2))

            root.enqueue()
        }

    }
}