package com.mertoenjosh.tabiandating

import com.mertoenjosh.tabiandating.models.User

interface IMainActivity {
    fun inflateViewProfileFragment(user: User)
}