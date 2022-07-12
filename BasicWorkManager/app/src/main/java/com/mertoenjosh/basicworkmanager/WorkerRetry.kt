package com.mertoenjosh.basicworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerRetry(context: Context, parameters: WorkerParameters): Worker(context, parameters) {
    override fun doWork(): Result {
        println("Still working: ${WorkStatusSingleton.workRetries}...")
        return if (WorkStatusSingleton.workRetries < 3) {
            WorkStatusSingleton.workRetries += 1
            Result.retry()
        } else {
            Result.success()
        }
    }
}