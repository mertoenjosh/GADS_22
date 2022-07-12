package com.mertoenjosh.coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    // using stateFlow
    private var _loginStatus = MutableStateFlow<Boolean>(false)
    val loginStatus = _loginStatus.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            delay(2000)
            _loginStatus.value = password == "pass"
//            _loginStatusLD.value = password == "pass"
        }
    }

    // Using livedata
//    private var _loginStatusLD = MutableLiveData<Boolean>()
//    val loginStatusLD: LiveData<Boolean>
//        get() {
//            return _loginStatusLD
//        }
}