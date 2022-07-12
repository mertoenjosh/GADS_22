package com.mertoenjosh.basicworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerFail(context: Context, parameters: WorkerParameters): Worker(context, parameters) {
    override fun doWork(): Result {
        return Result.failure()
    }
}