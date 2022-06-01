package com.mertoenjosh.tabiandating

import com.mertoenjosh.tabiandating.models.Message
import com.mertoenjosh.tabiandating.models.User

interface IMainActivity {
    fun inflateViewProfileFragment(user: User)
    fun onMessageSelected(message: Message)
    fun onBackPressed()
}