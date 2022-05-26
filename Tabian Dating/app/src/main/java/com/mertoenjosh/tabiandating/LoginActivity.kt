package com.mertoenjosh.tabiandating

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d(TAG, "onCreate: starting")
        mLogin = findViewById(R.id.btn_login)
        mLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                Log.d(TAG, "onClick: Logging in the user")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "LoginActivityTAG"
    }
}