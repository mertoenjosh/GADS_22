package com.mertoenjosh.workchains

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

import kotlin.random.*

class NetworkRequestWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        Thread.sleep(1000)
        println("${this::class.java.name}")

        val success = inputData.getBoolean("SUCCESS", false)
        val name = inputData.getString("NAME")
        return if (success) {
            name?.let { println("$name success")}
            val (color, product) = ProductDatabase.products[Random.nextInt(0, ProductDatabase.products.size)]
            println("Recommended $color $product")
            Result.success()
        } else {
            name?.let { println("$name failure")}
            Result.failure()
        }
    }
}