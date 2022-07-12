package com.mertoenjosh.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btnLogin: Button
    private lateinit var llDefault: LinearLayout
    private lateinit var llSuccess: LinearLayout

    private val vm by lazy {ViewModelProvider(this)[LoginViewModel::class.java]}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        llDefault = findViewById(R.id.llDefault)
        llSuccess = findViewById(R.id.llSuccess)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnLogin = findViewById(R.id.loginSend)

        btnLogin.setOnClickListener {
            vm.login(username.text.toString(), password.text.toString())
        }

        subscribeToLifeCycleEvent()
    }

    private fun subscribeToLifeCycleEvent() {
        // observe using stateFlow
        lifecycleScope.launchWhenStarted {

            vm.loginStatus.collectLatest {
                if (it) {
                    llDefault.visibility = View.GONE
                    llSuccess.visibility = View.VISIBLE
                } else {
                    llDefault.visibility = View.VISIBLE
                    llSuccess.visibility = View.GONE
                }
            }

            // observe using livedata
        /*
            vm.loginStatusLD.observe(this@LoginActivity) {
                if (it) {
                    llDefault.visibility = View.GONE
                    llSuccess.visibility = View.VISIBLE
                } else {
                    llDefault.visibility = View.VISIBLE
                    llSuccess.visibility = View.GONE
                }
            }
        */

        }
    }
}