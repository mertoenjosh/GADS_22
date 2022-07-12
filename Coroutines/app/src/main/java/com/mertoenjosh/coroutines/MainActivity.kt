package com.mertoenjosh.coroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    //    private var scope = CoroutineScope(Dispatchers.Main)

    private var i = 0
    private lateinit var btnCount: Button
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCount = findViewById(R.id.button)
        btnLogin = findViewById(R.id.login)
        btnCount.text = "$i"

        btnCount.setOnClickListener {
//            val job = scope.launch {
            val job = lifecycleScope.launch {
                for (j in 1..10) {
                    i += 1
                    btnCount.text = "$i"
                    Log.d(TAG, "onCreate: $i")
                    delay(1000)
                }
            }
            job.invokeOnCompletion {
                Log.d(TAG, "onCreate: Coroutine finished")
            }
        }
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onDestroy() {
//        scope.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivityTAG"
    }
}